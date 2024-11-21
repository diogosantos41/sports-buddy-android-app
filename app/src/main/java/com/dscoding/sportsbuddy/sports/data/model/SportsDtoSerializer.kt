package com.dscoding.sportsbuddy.sports.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object SportsDtoSerializer : KSerializer<SportDto> {

    const val INVALID_ID = "invalid_id"

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = SportDto::class.simpleName!!
    ) {
        element<String>("i") // sport id
        element<String>("d") // sport name
        element<List<JsonObject>>("e") // sport events
    }

    override fun deserialize(decoder: Decoder): SportDto = decoder.decodeStructure(descriptor) {
        var id: String? = null
        var name: String? = null
        var events: List<SportDto.EventDto> = emptyList()

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> id = decodeStringElement(descriptor, index)
                1 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This serializer only works with JSON"
                    )
                    val element = jsonDecoder.decodeJsonElement()

                    name = if (element is JsonPrimitive && element.isString) {
                        element.content
                    } else {
                        // If it's not a valid string, ignore it
                        null
                    }
                }

                2 -> events = decodeSerializableElement(
                    descriptor,
                    index,
                    ListSerializer(SportDto.EventDto.serializer())
                )

                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Invalid index")
            }
        }

        SportDto(
            id = id ?: INVALID_ID,
            name = name ?: "",
            events = events
        )
    }

    override fun serialize(encoder: Encoder, value: SportDto) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.id)
            encodeStringElement(descriptor, 1, value.name)
            encodeSerializableElement(
                descriptor,
                2,
                ListSerializer(SportDto.EventDto.serializer()),
                value.events
            )
        }
}