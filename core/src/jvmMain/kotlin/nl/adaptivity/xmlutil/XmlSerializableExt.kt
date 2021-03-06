/*
 * Copyright (c) 2018.
 *
 * This file is part of XmlUtil.
 *
 * This file is licenced to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You should have received a copy of the license with the source distribution.
 * Alternatively, you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
@file:JvmName("XmlSerializableExtJvm")

package nl.adaptivity.xmlutil

import java.io.*

/**
 * Extension functions for writing that need different js/jvm implementations
 */
fun XmlSerializable.toCharArray(): CharArray {
    val caw = CharArrayWriter()
    XmlStreaming.newWriter(caw).use { writer ->
        serialize(writer)
    }
    return caw.toCharArray()
}


@Throws(XmlException::class)
fun XmlSerializable.toReader(): Reader {
    val buffer = CharArrayWriter()
    XmlStreaming.newWriter(buffer).use {
        serialize(it)

    }
    return CharArrayReader(buffer.toCharArray())
}

@Throws(XmlException::class)
fun XmlSerializable.serialize(writer: Writer) {
    XmlStreaming.newWriter(writer, repairNamespaces = true, omitXmlDecl = true).use { serialize(it) }
}

fun XmlSerializable.toString(flags: Int): String {
    return StringWriter().apply {
        XmlStreaming.newWriter(
            this, flags.and(
                FLAG_REPAIR_NS
                           ) == FLAG_REPAIR_NS, true
                              ).use { writer ->
            serialize(writer)
        }
    }.toString()
}