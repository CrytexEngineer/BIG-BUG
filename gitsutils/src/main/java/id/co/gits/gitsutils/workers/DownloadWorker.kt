package id.co.gits.gitsutils.workers


//class DownloadWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
//
//    override fun doWork(): Result {
//
//        val uriFile = runBlocking {
//            getFileFromUrlChannel(
//                    context,
//                    inputData.getString(EXTRA_CERTIFICATE_ITEM) ?: "facebook.com"
//            ).receive()
//        }
//        return if (uriFile != null) {
//            outputData = Data.Builder()
//                    .putString(EXTRA_CERTIFICATE_ITEM, uriFile.path)
//                    .build()
//
//            Result.SUCCESS
//        } else {
//            Result.FAILURE
//        }
//    }
//
//    companion object {
//
//        private val TAG = DownloadWorker::class.java.simpleName
//    }
//}
