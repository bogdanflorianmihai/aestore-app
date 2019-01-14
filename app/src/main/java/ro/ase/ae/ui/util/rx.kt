@file:Suppress("unused")

package ro.ase.ae.ui.util

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.asCoroutineDispatcher

val ioDispatcher = Schedulers.io().asCoroutineDispatcher()

fun <T> Single<T>.mainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.mainThread(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.mainThread(): Flowable<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.mainThread(): Maybe<T> = observeOn(AndroidSchedulers.mainThread())

fun Completable.mainThread(): Completable = observeOn(AndroidSchedulers.mainThread())


fun <T> Single<T>.ioSubscribe(): Single<T> = subscribeOn(Schedulers.io())
fun <T> Observable<T>.ioSubscribe(): Observable<T> = subscribeOn(Schedulers.io())
fun <T> Flowable<T>.ioSubscribe(): Flowable<T> = subscribeOn(Schedulers.io())
fun <T> Maybe<T>.ioSubscribe(): Maybe<T> = subscribeOn(Schedulers.io())
fun Completable.ioSubscribe(): Completable = subscribeOn(Schedulers.io())