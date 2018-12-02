package com.sano.reditto.domain.usecase

import com.sano.reditto.di.manager.SessionManager
import com.sano.reditto.domain.IRepository
import com.sano.reditto.domain.entity.TopTimeEntity
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.presentation.model.mapper.toLinkModel
import io.reactivex.Single

private const val LINK_PREFIX = "https://www.reddit.com/"

class MainUseCase(private val repository: IRepository, private val sessionManager: SessionManager) {
    private var after: String? = null
    private val time = TopTimeEntity.DAY
    val pageSize: Int = 25

    fun getTop(count: Int = 0): Single<List<LinkModel>> {
        if (count == 0) after = null

        return repository.getTop(time.value, after, count, false, pageSize)
            .doOnSuccess { after = it.after }
            .map { list -> list.links.map { it.toLinkModel() } }
    }

    fun revokeAccessToken() = repository.revokeAccessToken()

    fun revokeRefreshToken() = repository.revokeRefreshToken()

    fun logout() {
        repository.logout()
        sessionManager.onLoggedOut()
    }

    fun formatLink(link: LinkModel) = LINK_PREFIX + link.id

}