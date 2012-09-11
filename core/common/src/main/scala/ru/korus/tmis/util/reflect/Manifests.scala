package ru.korus.tmis.util.reflect

import reflect.Manifest

object Manifests {

  def manifestFromClass(clazz: Class[_]): Manifest[_] = {
    if (clazz.isPrimitive) {
      Map[Class[_], Manifest[_]](
        classOf[Unit]     -> Manifest.Unit,
        classOf[Boolean]  -> Manifest.Boolean,
        classOf[Byte]     -> Manifest.Byte,
        classOf[Char]     -> Manifest.Char,
        classOf[Short]    -> Manifest.Short,
        classOf[Int]      -> Manifest.Int,
        classOf[Long]     -> Manifest.Long,
        classOf[Float]    -> Manifest.Float,
        classOf[Double]   -> Manifest.Double
      ) (clazz)
    } else {
      val dims = clazz.getTypeParameters.collect{ case ignore => Manifest.wildcardType(manifest[Nothing], manifest[Any]) }
      if (dims.size == 0) {
        Manifest.classType(clazz)
      }
      else {
        Manifest.classType(clazz, dims.head, dims.tail:_*)
      }
    }
  }
  
  def classForValue(v: Any): Class[_] = {
    v match {
      case _: Unit     => classOf[Unit]
      case _: Boolean  => classOf[Boolean]
      case _: Byte     => classOf[Byte]
      case _: Char     => classOf[Char]
      case _: Short    => classOf[Short]
      case _: Long     => classOf[Long]
      case _: Int      => classOf[Int]
      case _: Float    => classOf[Float]
      case _: Double   => classOf[Double]

      case ref: AnyRef => ref.getClass
    }
  }

  def runtimeManifestForValue(v: Any) = manifestFromClass(classForValue(v))
  
  def box(v: Any): AnyRef = {
    v match {
      case ref: AnyRef => ref
    }
  }

  def boxClass(clazz: Class[_]): Class[_] = {
    import java.{lang => jl}
    if (clazz.isPrimitive) {
      Map[Class[_], Class[_]](
        classOf[Unit]     -> classOf[jl.Void],
        classOf[Boolean]  -> classOf[jl.Boolean],
        classOf[Byte]     -> classOf[jl.Byte],
        classOf[Char]     -> classOf[jl.Character],
        classOf[Short]    -> classOf[jl.Short],
        classOf[Int]      -> classOf[jl.Integer],
        classOf[Long]     -> classOf[jl.Long],
        classOf[Float]    -> classOf[jl.Float],
        classOf[Double]   -> classOf[jl.Double]
      ) (clazz)
    } else clazz
  }

  def canAssign(to: Class[_], from: Class[_]): Boolean = {
    if(from.isPrimitive && !to.isPrimitive) {
      to.isAssignableFrom(boxClass(from))
    } else {
      to.isAssignableFrom(from)
    }
  }

  def actuallyWorkingClassOf[T: Manifest]: Class[T] = {
    manifest[T].erasure.asInstanceOf[Class[T]]
  }
}