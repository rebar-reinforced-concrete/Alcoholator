package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel

interface UserStateCache {
    fun storeUserState(userStateDataModel: UserStateDataModel)
    fun retrieveUserState(): UserStateDataModel?
}
