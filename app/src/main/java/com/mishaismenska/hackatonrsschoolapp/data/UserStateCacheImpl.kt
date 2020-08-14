package com.mishaismenska.hackatonrsschoolapp.data

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.UserStateCache
import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel
import javax.inject.Inject

class UserStateCacheImpl @Inject constructor() : UserStateCache {
    private var userStateDataModel: UserStateDataModel? = null
    override fun storeUserState(userStateDataModel: UserStateDataModel) {
       this.userStateDataModel = userStateDataModel
    }

    override fun retrieveUserState(): UserStateDataModel? {
        return userStateDataModel
    }
}
