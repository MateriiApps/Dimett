package xyz.wingio.dimett

import android.app.Application
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import xyz.wingio.dimett.di.databaseModule
import xyz.wingio.dimett.di.httpModule
import xyz.wingio.dimett.di.loggerModule
import xyz.wingio.dimett.di.managerModule
import xyz.wingio.dimett.di.repositoryModule
import xyz.wingio.dimett.di.serviceModule
import xyz.wingio.dimett.di.viewModelModule

class Dimett : Application() {

    override fun onCreate() {
        super.onCreate()

        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        Coil.setImageLoader(imageLoader)

        startKoin {
            androidContext(this@Dimett)

            modules(
                viewModelModule,
                databaseModule,
                managerModule,
                httpModule,
                serviceModule,
                repositoryModule,
                loggerModule
            )
        }

    }

}