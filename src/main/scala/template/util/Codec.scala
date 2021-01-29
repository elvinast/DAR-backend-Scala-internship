package template.util

import template.model.{Summary, User}

trait Codec {

  implicit val userEncodeDecode: EncoderDecoder[User] = DerivedEncoderDecoder[User]

  implicit val summaryEncodeDecode: EncoderDecoder[Summary] = DerivedEncoderDecoder[Summary]

}