package br.com.teste.common.di

import br.com.teste.BaseApplication
import br.com.teste.common.di.builder.ActivityBuilder
import br.com.teste.common.di.builder.ViewModelBuilder
import br.com.teste.common.di.module.ApiModule
import br.com.teste.common.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        ActivityBuilder::class,
        ApiModule::class,
        RepositoryModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Builder : AndroidInjector.Factory<BaseApplication> {
        override fun create(@BindsInstance instance: BaseApplication): AndroidInjector<BaseApplication>
    }
}
