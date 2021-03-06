package com.iceteaviet.fastfoodfinder.data.domain.store

import androidx.annotation.VisibleForTesting
import com.iceteaviet.fastfoodfinder.data.local.db.store.StoreDataSource
import com.iceteaviet.fastfoodfinder.data.remote.store.StoreApiHelper
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Comment
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Store
import com.iceteaviet.fastfoodfinder.utils.exception.NotFoundException
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe

/**
 * Created by tom on 2019-06-01.
 */
class AppStoreRepository(private val storeApiHelper: StoreApiHelper, private val storeDataSource: StoreDataSource) : StoreRepository {

    /**
     * This method has reduced visibility for testing and is only visible to tests in the same
     * package.
     */
    @VisibleForTesting
    private var cachedStores: List<Store>

    init {
        cachedStores = ArrayList()
    }

    override fun getAllStores(): Single<List<Store>> {
        return Single.create(SingleOnSubscribe { emitter ->
            if (cachedStores.isNotEmpty()) {
                emitter.onSuccess(cachedStores)
                return@SingleOnSubscribe
            }

            cachedStores = storeDataSource.getAllStores()

            if (cachedStores.isEmpty()) {
                storeApiHelper.getAllStores(object : StoreApiHelper.StoreLoadCallback<List<Store>> {
                    override fun onSuccess(data: List<Store>) {
                        cachedStores = data
                        emitter.onSuccess(ArrayList(data))
                    }

                    override fun onError(exception: Exception) {
                        emitter.onError(exception)
                    }

                })
            } else {
                emitter.onSuccess(ArrayList(cachedStores))
            }
        })
    }

    override fun setStores(storeList: List<Store>) {
        cachedStores = storeList
        storeDataSource.setStores(storeList)
    }

    override fun getStoreInBounds(lat: Double, lng: Double, radius: Double): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.getStoreInBounds(lat, lng, radius))
        }
    }

    override fun findStores(queryString: String): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.findStores(queryString))
        }
    }

    override fun findStoresByCustomAddress(customQuerySearch: List<String>): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.findStoresByCustomAddress(customQuerySearch))
        }
    }

    override fun findStoresBy(key: String, value: Int): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.findStoresBy(key, value))
        }
    }

    override fun findStoresBy(key: String, values: List<Int>): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.findStoresBy(key, values))
        }
    }

    override fun findStoresByType(type: Int): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.findStoresByType(type))
        }
    }

    override fun findStoreById(id: Int): Single<Store> {
        return Single.create { emitter ->
            val store = storeDataSource.findStoreById(id)
            if (store != null)
                emitter.onSuccess(store)
            else
                emitter.onError(NotFoundException())
        }
    }

    override fun findStoresByIds(ids: List<Int>): Single<List<Store>> {
        return Single.create { emitter ->
            emitter.onSuccess(storeDataSource.findStoresByIds(ids))
        }
    }

    override fun deleteAllStores() {
        cachedStores = ArrayList()
        storeDataSource.deleteAllStores()
    }

    override fun getComments(storeId: String): Single<List<Comment>> {
        return Single.create { emitter ->
            storeApiHelper.getComments(storeId, object : StoreApiHelper.StoreLoadCallback<List<Comment>> {
                override fun onSuccess(data: List<Comment>) {
                    emitter.onSuccess(data)
                }

                override fun onError(exception: Exception) {
                    emitter.onError(exception)
                }

            })
        }
    }

    override fun insertOrUpdateComment(storeId: String, comment: Comment) {
        return storeApiHelper.insertOrUpdateComment(storeId, comment)
    }

    fun clearCache() {
        cachedStores = ArrayList()
    }
}