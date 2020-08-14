package com.mishaismenska.hackatonrsschoolapp.data.interfaces

import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel

interface UserStateCache {
    fun storeUserState(userStateDataModel: UserStateDataModel)
    fun retrieveUserState(): UserStateDataModel?
}
