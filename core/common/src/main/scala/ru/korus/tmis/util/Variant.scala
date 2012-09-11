package ru.korus.tmis.util


sealed trait Variant[+A,+B,+C,+D,+E,+F,+G,+H,+I,+J] {
  protected def get0: Option[A] = None
  protected def get1: Option[B] = None
  protected def get2: Option[C] = None
  protected def get3: Option[D] = None
  protected def get4: Option[E] = None
  protected def get5: Option[F] = None
  protected def get6: Option[G] = None
  protected def get7: Option[H] = None
  protected def get8: Option[I] = None
  protected def get9: Option[J] = None
}

object Variant{
  private type N = Nothing
  
  sealed trait MinArity10[+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
  sealed trait MinArity9 [+A,+B,+C,+D,+E,+F,+G,+H,+I] extends MinArity10[A,B,C,D,E,F,G,H,I,N]
  sealed trait MinArity8 [+A,+B,+C,+D,+E,+F,+G,+H] extends MinArity9[A,B,C,D,E,F,G,H,N]
  sealed trait MinArity7 [+A,+B,+C,+D,+E,+F,+G] extends MinArity8[A,B,C,D,E,F,G,N]
  sealed trait MinArity6 [+A,+B,+C,+D,+E,+F] extends MinArity7[A,B,C,D,E,F,N]
  sealed trait MinArity5 [+A,+B,+C,+D,+E] extends MinArity6[A,B,C,D,E,N]
  sealed trait MinArity4 [+A,+B,+C,+D] extends MinArity5[A,B,C,D,N]
  sealed trait MinArity3 [+A,+B,+C] extends MinArity4[A,B,C,N]
  sealed trait MinArity2 [+A,+B] extends MinArity3[A,B,N]

  sealed trait MaxArity10[+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity9[A,B,C,D,E,F,G,H,I,J] {
    override def get9 = super.get9
  }
  sealed trait MaxArity9 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity8[A,B,C,D,E,F,G,H,I,J] {
    override def get8 = super.get8
  }
  sealed trait MaxArity8 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity7[A,B,C,D,E,F,G,H,I,J] {
    override def get7 = super.get7
  }
  sealed trait MaxArity7 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity6[A,B,C,D,E,F,G,H,I,J] {
    override def get6 = super.get6
  }
  sealed trait MaxArity6 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity5[A,B,C,D,E,F,G,H,I,J] {
    override def get5 = super.get5
  }
  sealed trait MaxArity5 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity4[A,B,C,D,E,F,G,H,I,J] {
    override def get4 = super.get4
  }
  sealed trait MaxArity4 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity3[A,B,C,D,E,F,G,H,I,J] {
    override def get3 = super.get3
  }
  sealed trait MaxArity3 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends MaxArity2[A,B,C,D,E,F,G,H,I,J] {
    override def get2 = super.get2
  }
  sealed trait MaxArity2 [+A,+B,+C,+D,+E,+F,+G,+H,+I,+J]
      extends Variant[A,B,C,D,E,F,G,H,I,J] {
    override def get1 = super.get1
    override def get0 = super.get0
  }



  sealed case class Variant0[+X](v: X)
      extends Variant[X,N,N,N,N,N,N,N,N,N]
      with MinArity2[X,N]
      with MaxArity10[X,N,N,N,N,N,N,N,N,N]{ override def get0 = Some(v) }
  sealed case class Variant1[+X](v: X)
      extends Variant[N,X,N,N,N,N,N,N,N,N]
      with MinArity2[N,X]
      with MaxArity10[N,X,N,N,N,N,N,N,N,N]{ override def get1 = Some(v) }
  sealed case class Variant2[+X](v: X)
      extends Variant[N,N,X,N,N,N,N,N,N,N]
      with MinArity3[N,N,X]
      with MaxArity10[N,N,X,N,N,N,N,N,N,N]{ override def get2 = Some(v) }
  sealed case class Variant3[+X](v: X)
      extends Variant[N,N,N,X,N,N,N,N,N,N]
      with MinArity4[N,N,N,X]
      with MaxArity10[N,N,N,X,N,N,N,N,N,N]{ override def get3 = Some(v) }
  sealed case class Variant4[+X](v: X)
      extends Variant[N,N,N,N,X,N,N,N,N,N]
      with MinArity5[N,N,N,N,X]
      with MaxArity10[N,N,N,N,X,N,N,N,N,N]{ override def get4 = Some(v) }
  sealed case class Variant5[+X](v: X)
      extends Variant[N,N,N,N,N,X,N,N,N,N]
      with MinArity6[N,N,N,N,N,X]
      with MaxArity10[N,N,N,N,N,X,N,N,N,N]{ override def get5 = Some(v) }
  sealed case class Variant6[+X](v: X)
      extends Variant[N,N,N,N,N,N,X,N,N,N]
      with MinArity7[N,N,N,N,N,N,X]
      with MaxArity10[N,N,N,N,N,N,X,N,N,N]{ override def get6 = Some(v) }
  sealed case class Variant7[+X](v: X)
      extends Variant[N,N,N,N,N,N,N,X,N,N]
      with MinArity8[N,N,N,N,N,N,N,X]
      with MaxArity10[N,N,N,N,N,N,N,X,N,N]{ override def get7 = Some(v) }
  sealed case class Variant8[+X](v: X)
      extends Variant[N,N,N,N,N,N,N,N,X,N]
      with MinArity9[N,N,N,N,N,N,N,N,X]
      with MaxArity10[N,N,N,N,N,N,N,N,X,N]{ override def get8 = Some(v) }
  sealed case class Variant9[+X](v: X)
      extends Variant[N,N,N,N,N,N,N,N,N,X]
      with MinArity10[N,N,N,N,N,N,N,N,N,X]
      with MaxArity10[N,N,N,N,N,N,N,N,N,X]{ override def get9 = Some(v) }

  type EitherOf10[+A,+B,+C,+D,+E,+F,+G,+H,+I,+J] = Variant[A,B,C,D,E,F,G,H,I,J] with MinArity10[A,B,C,D,E,F,G,H,I,J] with MaxArity10[A,B,C,D,E,F,G,H,I,J]
  type EitherOf9[+A,+B,+C,+D,+E,+F,+G,+H,+I] = Variant[A,B,C,D,E,F,G,H,I,N] with MinArity9[A,B,C,D,E,F,G,H,I] with MaxArity9[A,B,C,D,E,F,G,H,I,N]
  type EitherOf8[+A,+B,+C,+D,+E,+F,+G,+H] = EitherOf9[A,B,C,D,E,F,G,H,N] with MinArity8[A,B,C,D,E,F,G,H] with MaxArity8[A,B,C,D,E,F,G,H,N,N]
  type EitherOf7[+A,+B,+C,+D,+E,+F,+G] = EitherOf8[A,B,C,D,E,F,G,N] with MinArity7[A,B,C,D,E,F,G] with MaxArity7[A,B,C,D,E,F,G,N,N,N]
  type EitherOf6[+A,+B,+C,+D,+E,+F] = EitherOf7[A,B,C,D,E,F,N] with MinArity6[A,B,C,D,E,F] with MaxArity6[A,B,C,D,E,F,N,N,N,N]
  type EitherOf5[+A,+B,+C,+D,+E] = EitherOf6[A,B,C,D,E,N] with MinArity5[A,B,C,D,E]  with MaxArity5[A,B,C,D,E,N,N,N,N,N]
  type EitherOf4[+A,+B,+C,+D] = EitherOf5[A,B,C,D,N] with MinArity4[A,B,C,D] with MaxArity4[A,B,C,D,N,N,N,N,N,N]
  type EitherOf3[+A,+B,+C] = EitherOf4[A,B,C,N] with MinArity3[A,B,C] with MaxArity3[A,B,C,N,N,N,N,N,N,N]
  type EitherOf2[+A,+B] = EitherOf3[A,B,N] with MinArity2[A,B] with MaxArity2[A,B,N,N,N,N,N,N,N,N]
}



