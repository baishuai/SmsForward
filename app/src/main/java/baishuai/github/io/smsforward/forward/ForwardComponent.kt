package baishuai.github.io.smsforward.forward

import baishuai.github.io.smsforward.forward.directsms.DirectSmsRepo
import baishuai.github.io.smsforward.forward.feige.FeigeApi
import baishuai.github.io.smsforward.forward.slack.SlackApi
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by bai on 17-5-1.
 */

@Singleton
@Subcomponent(modules = arrayOf(ForwardModule::class))
interface ForwardComponent {


    fun directSmsRepo(): DirectSmsRepo

    fun feigeApi(): FeigeApi

    fun slackApi(): SlackApi
}