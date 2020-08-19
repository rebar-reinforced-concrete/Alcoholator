package com.mishaismenska.hackatonrsschoolapp.data

import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UserStateRepository
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import javax.inject.Inject

class UserStateRepositoryImpl @Inject constructor() : UserStateRepository {

    private var userStateDataModel: UserStateDataModel? = null

    override fun storeUserState(userStateDataModel: UserStateDomainModel) {
        this.userStateDataModel = UserStateDataModel(
            userStateDataModel.alcoholConcentration,
            userStateDataModel.alcoholDepletionDuration,
            userStateDataModel.lastUpdateTime
        )
    }

    override fun retrieveUserState(): UserStateDomainModel? {
        return if (userStateDataModel == null) {
            null
        } else {
            val userState = userStateDataModel!!
            UserStateDomainModel(userState.alcoholConcentration, userState.alcoholDepletionDuration, userState.lastUpdateTime)
        }
    }
}
