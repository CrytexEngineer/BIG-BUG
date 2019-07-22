/*
package id.co.gits.gitsutils

import id.co.gits.gitsutils.extensions.loggerDebug
import id.co.gits.gitsutils.extensions.withBearerAuthTokenPrefix
import id.gits.bidanconnect.EnutriApplication
import id.gits.bidanconnect.data.param.LoginParam
import id.gits.bidanconnect.data.source.remote.auth.refresh.delegate.MixedAuthCredentialsDelegate
import id.gits.bidanconnect.data.source.remote.v2.AccountApiService
import id.co.gits.gitsutils.EnutriHelper.Const.BOGUS_JWT
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials

class SecuredCredentialStorage(
        val app: EnutriApplication
) : MixedAuthCredentialsDelegate {

    // This is set after the API is injected:
    var api: AccountApiService? = null

    override fun userBasicAuthCredentials(): String =
            Credentials.basic(app.userCode, app.password)

    // TODO: Change this according to real apps
    override fun clientBasicAuthCredentials(): String = Credentials.basic(
            "",
            ""
    )

    override fun userOauthBearerToken(): String {
        return bearerToken().withBearerAuthTokenPrefix()
    }

    override fun userOauthRefreshToken(): String {
        return refreshToken()
    }

    override fun userOauthRefreshedBearerToken(): String? {

        // Call the authToken refresh:
        loggerDebug("// Call the authToken refresh:")
        val response = runBlocking { api?.login(LoginParam(app.userCode, app.password))?.await() }

        // Check for success:
        loggerDebug("// Check for success:")
        if (response?.success == true) {
            // refresh succeeded! get the authToken response:
            loggerDebug("// refresh succeeded! get the authToken response:")
            response.let { tokenResponse ->
                // Got it!  Store the tokens:
                loggerDebug("// Got it!  Store the tokens:")
                setUserTokens(
                        tokenResponse.data?.token ?: BOGUS_JWT,
                        tokenResponse.data?.token ?: BOGUS_JWT)
                // Return the new access authToken:
                loggerDebug("// Return the new access authToken:")
                return tokenResponse.data?.token?.withBearerAuthTokenPrefix()
            }
        }
        return null
    }

    fun setUserTokens(bearerToken: String, refreshToken: String) {
        app.apply {
            this.authToken = bearerToken
            this.refreshToken = refreshToken
        }
    }

    fun setUserBasicCredentials(username: String, password: String) {
        app.apply {
            this.isLoggedIn = username.isNotBlank() and password.isNotBlank()
            this.userCode = username
            this.password = password
        }
    }

    override fun userOauthSessionNotRecoverable() {
        logUserOut()
    }

    fun logUserOut() {

        // throw out any session cookies used by services:
        // cookieJar.clear()

        // wipe out our user's basic credentials:
        setUserBasicCredentials("", "")

        // wipe out our user's oauth tokens:
        setUserTokens("", "")

        // direct the app to return to login
        app.returnToLogin()
    }

    private fun bearerToken(): String = app.authToken ?: BOGUS_JWT
    private fun refreshToken(): String = app.refreshToken ?: BOGUS_JWT
}*/
