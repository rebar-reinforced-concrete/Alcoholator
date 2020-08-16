package com.mishaismenska.hackatonrsschoolapp.data

import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UserStateCache
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
