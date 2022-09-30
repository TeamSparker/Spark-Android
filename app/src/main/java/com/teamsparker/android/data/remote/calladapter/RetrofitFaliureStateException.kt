package com.teamsparker.android.data.remote.calladapter

class RetrofitFailureStateException(error: String?, val code: Int) : Exception(error)
