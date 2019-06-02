package com.iceteaviet.fastfoodfinder.ui.storelist

import android.content.Intent
import com.iceteaviet.fastfoodfinder.data.DataManager
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Store
import com.iceteaviet.fastfoodfinder.data.remote.user.model.UserStoreList
import com.iceteaviet.fastfoodfinder.ui.base.BasePresenter
import com.iceteaviet.fastfoodfinder.utils.rx.SchedulerProvider
import com.iceteaviet.fastfoodfinder.utils.ui.getStoreListIconDrawableRes
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Created by tom on 2019-04-18.
 */
class ListDetailPresenter : BasePresenter<ListDetailContract.Presenter>, ListDetailContract.Presenter {

    private val listDetailView: ListDetailContract.View

    private var userStoreList: UserStoreList? = null
    private var photoUrl: String? = null

    constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, listDetailView: ListDetailContract.View) : super(dataManager, schedulerProvider) {
        this.listDetailView = listDetailView
        this.listDetailView.presenter = this
    }

    override fun subscribe() {
        userStoreList?.let {
            listDetailView.setListNameText(it.listName)
            listDetailView.loadStoreIcon(getStoreListIconDrawableRes(it.iconId))

            //add list store to mAdapter here
            dataManager.findStoresByIds(it.getStoreIdList())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(object : SingleObserver<List<Store>> {
                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onSuccess(storeList: List<Store>) {
                            listDetailView.setStores(storeList)
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })
        }
    }

    override fun handleExtras(intent: Intent?) {
        if (intent == null) {
            listDetailView.exit()
            return
        }

        userStoreList = UserStoreList()
        if (intent.hasExtra(ListDetailActivity.KEY_USER_STORE_LIST))
            userStoreList = intent.getParcelableExtra(ListDetailActivity.KEY_USER_STORE_LIST)
        photoUrl = intent.getStringExtra(ListDetailActivity.KEY_USER_PHOTO_URL)
    }
}