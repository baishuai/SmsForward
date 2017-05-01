package baishuai.github.io.smsforward.forward

import baishuai.github.io.smsforward.forward.feige.FeigeRepo
import baishuai.github.io.smsforward.forward.slack.SlackRepo
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by bai on 17-5-1.
 */

@Singleton
@Subcomponent(modules = arrayOf(ForwardModule::class))
interface ForwardComponent {

    fun feigeRepo(): FeigeRepo

    fun slackRepo(): SlackRepo
}