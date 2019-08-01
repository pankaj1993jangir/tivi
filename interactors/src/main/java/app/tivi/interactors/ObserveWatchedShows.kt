/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.tivi.interactors

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import app.tivi.data.entities.SortOption
import app.tivi.data.repositories.watchedshows.WatchedShowsRepository
import app.tivi.data.resultentities.WatchedShowEntryWithShow
import app.tivi.extensions.asFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWatchedShows @Inject constructor(
    private val watchedShowsRepository: WatchedShowsRepository
) : PagingInteractor<ObserveWatchedShows.Params, WatchedShowEntryWithShow>() {

    override fun createObservable(params: Params): Flow<PagedList<WatchedShowEntryWithShow>> {
        val source = watchedShowsRepository.observeWatchedShowsPagedList(params.filter, params.sort)
        return LivePagedListBuilder(source, params.pagingConfig)
                .setBoundaryCallback(params.boundaryCallback)
                .build()
                .asFlow()
    }

    data class Params(
        val filter: String? = null,
        val sort: SortOption,
        override val pagingConfig: PagedList.Config,
        override val boundaryCallback: PagedList.BoundaryCallback<WatchedShowEntryWithShow>?
    ) : Parameters<WatchedShowEntryWithShow>
}
