package me.jorgecastillo.viper.common.data.network

import arrow.core.Either
import arrow.core.Try
import arrow.core.left
import arrow.core.right
import me.jorgecastillo.viper.common.data.network.mapper.toDomain
import me.jorgecastillo.viper.common.data.network.service.UnsplashService
import me.jorgecastillo.viper.common.domain.error.Error
import me.jorgecastillo.viper.photoslist.domain.model.Photo

class PhotosNotFound : Error.FeatureError()

class UnsplashPhotosDataSource(private val service: UnsplashService) : PhotosNetworkDataSource {

  override fun getAll(): Either<Error, List<Photo>> =
      Try {
        service.getPhotos().execute()
      }.fold(ifFailure = {
        Error.ServerError().left()
      }, ifSuccess = { response ->
        if (response.isSuccessful) {
          val body = response.body()!!
          body.toDomain().right()
        } else PhotosNotFound().left()
      })

  fun getPhoto(id: String): Either<Error, Photo> {
    val response = service.getPhoto(id).execute()
    return if (response.isSuccessful) {
      response.body()!!.toDomain().right()
    } else PhotosNotFound().left()
  }
}
