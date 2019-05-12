package com.iceteaviet.fastfoodfinder.ui.store

import android.content.Intent
import com.iceteaviet.fastfoodfinder.data.remote.routing.model.MapsDirection
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Comment
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Store
import com.iceteaviet.fastfoodfinder.ui.base.BaseView

/**
 * Created by tom on 2019-04-18.
 */
interface StoreDetailContract {
    interface View : BaseView<Presenter> {
        fun setToolbarTitle(title: String)
        fun requestLocationUpdates()
        fun getLastLocation()
        fun setStoreComments(listComments: MutableList<Comment>)
        fun addStoreComment(comment: Comment)
        fun setAppBarExpanded(expanded: Boolean)
        fun scrollToCommentList()
        fun showCommentEditorView()
        fun startCallIntent(tel: String)
        fun showInvalidPhoneNumbWarning()
        fun showMapRoutingView(currStore: Store, mapsDirection: MapsDirection)
        fun exit()
    }

    interface Presenter : com.iceteaviet.fastfoodfinder.ui.base.Presenter {
        fun onLocationPermissionGranted()
        fun handleExtras(intent: Intent?)
        fun onAddNewComment(comment: Comment?)
        fun onCurrLocationChanged(latitude: Double, longitude: Double)
        fun onCommentButtonClick()
        fun onCallButtonClick(tel: String?)
        fun onNavigationButtonClick()
    }
}