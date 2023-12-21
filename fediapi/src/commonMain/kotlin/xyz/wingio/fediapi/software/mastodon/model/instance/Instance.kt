package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Language
import xyz.wingio.fediapi.URL
import xyz.wingio.fediapi.software.mastodon.model.Rule

/**
 * Represents the software instance of Mastodon running on this [domain].
 *
 * @param domain The domain name of the instance.
 * @param title The title of the website.
 * @param version The version of Mastodon installed on the instance.
 * @param sourceUrl The URL for the source code of the software running on this instance, in keeping with AGPL license requirements.
 * @param description A short, plain-text description defined by the admin.
 * @param usage Usage data for this instance.
 * @param thumbnail An image used to represent this instance.
 * @param languages Primary languages of the website and its staff.
 * @param configuration Configured values and limits for this website.
 * @param registrations Information about registering for this website.
 * @param contact Hints related to contacting a representative of the website.
 * @param rules An itemized list of rules for this website.
 */
@Serializable
public data class Instance(
    val domain: String,
    val title: String,
    val version: String,
    @SerialName("source_url") val sourceUrl: URL,
    val description: String,
    val usage: InstanceUsage,
    val thumbnail: InstanceThumbnail,
    val languages: List<Language>,
    val configuration: InstanceConfiguration,
    val registrations: InstanceRegistrationConfiguration,
    val contact: InstanceContact,
    val rules: List<Rule>
)
