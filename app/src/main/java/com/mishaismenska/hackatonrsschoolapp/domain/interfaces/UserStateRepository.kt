package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel

interface UserStateRepository {
    fun storeUserState(userStateDataModel: UserStateDomainModel)
    fun retrieveUserState(): UserStateDomainModel?
}
