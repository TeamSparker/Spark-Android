package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.ProfileDataSource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
}
