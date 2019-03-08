package app.junhyounglee.android.funnybee.ui.event

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Subscription

/**
 * Create: 8/03/19
 * Author: Jun Hyoung Lee
 * Email: niceguy0315@hotmail.com
 */
class RxEventManager private constructor() {

    private val mSubjects = PublishSubject.create<Any>().toSerialized()
    private val mRefCountMap: MutableMap<Class<*>, Int>
            = HashMap()

    private object Holder { val INSTANCE = RxEventManager() }

    /**
     * Post an event to subscribed handlers.
     * It can detect event is not handled.
     *
     * @param <E>       Type of `event`.
     * @param event     An event to post.
     * @param unhandled It will be called if `event` is not handled.
     * Note: If handler subscribed by using async [Scheduler], it can't guarantee `event` is actually handled.
    </E> */
    fun <E : Any> post(event: E, unhandled: (E) -> Unit) {
        if (getRefCount(event::class.java) > 0) {
            mSubjects.onNext(event)
        } else {
            unhandled.let { it(event) }
        }
    }

    /**
     * Post an event to subscribed handlers.
     * Do nothing on unhandled.
     *
     * @param <E>   Type of `event`.
     * @param event An event to post.
     * @see .post
    </E> */
    fun <E : Any> post(event: E) {
        if (getRefCount(event::class.java) > 0) {
            mSubjects.onNext(event)
        }
    }

    /**
     * Subscribe `handler` to receive events type of specified class.
     *
     *
     * You should call [Subscription.unsubscribe] if you want to stop receiving events.
     *
     * @param <E>       Type of `event`.
     * @param clazz     Type of event that you want to receive.
     * @param handler   It will be called when `clazz` and the same type of events were posted.
     * @param scheduler `handler` will dispatched to this scheduler.
     * @return A [Subscription] which can stop observing by calling [Subscription.unsubscribe].
    </E> */
    fun <E> subscribe(clazz: Class<E>, handler: (E) -> Unit, scheduler: Scheduler): Disposable? {
        addRefCount(clazz)

        return mSubjects.ofType(clazz)
            .doOnDispose { removeRefCount(clazz) }
            .observeOn(scheduler)
            .subscribe(handler)
    }

    /**
     * Subscribe `handler` to receive events type of specified class.
     *
     *
     * Handler scheduled by [Schedulers.immediate]
     *
     * @param <E>     Type of `event`.
     * @param clazz   Type of event that you want to receive.
     * @param handler It will be called when `clazz` and the same type of events were posted.
     * @return A [Subscription] which can stop observing by calling [Subscription.unsubscribe].
     * @see .subscribe
    </E> */
    fun <E> subscribe(clazz: Class<E>, handler: (E) -> Unit): Disposable? {
        return subscribe(clazz, handler, Schedulers.trampoline())
    }

    @Synchronized
    private fun getRefCount(clazz: Class<*>): Int {
        return if (mRefCountMap.containsKey(clazz)) {
            mRefCountMap[clazz]!!
        } else {
            0
        }
    }

    @Synchronized
    private fun setRefCount(clazz: Class<*>, refCount: Int) {
        if (refCount == 0) {
            mRefCountMap.remove(clazz)
        } else {
            mRefCountMap.put(clazz, refCount)
        }
    }

    @Synchronized
    private fun addRefCount(clazz: Class<*>) {
        setRefCount(clazz, getRefCount(clazz) + 1)
    }

    @Synchronized
    private fun removeRefCount(clazz: Class<*>) {
        setRefCount(clazz, getRefCount(clazz) - 1)
    }

    companion object {
        val instance: RxEventManager by lazy { Holder.INSTANCE }
    }
}
