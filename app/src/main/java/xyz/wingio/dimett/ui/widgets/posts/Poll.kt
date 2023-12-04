package xyz.wingio.dimett.ui.widgets.posts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ast.EmojiSyntakts
import xyz.wingio.dimett.ast.render
import xyz.wingio.dimett.rest.dto.Poll
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.utils.getString
import xyz.wingio.dimett.utils.toEmojiMap

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun Poll(
    poll: Poll,
    onVote: (String, List<Int>) -> Unit = { _, _ -> }
) {
    val emojiMap = poll.emojis.toEmojiMap()
    val selected = remember {
        val l = mutableStateListOf<Int>()
        l.addAll(poll.ownVotes)
        l
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in poll.options.indices) {
            val option = poll.options[i]
            val voted = selected.contains(i)
            val vote = {
                if (!poll.multiple) {
                    selected.clear()
                    selected.add(i)
                }

                if (voted && poll.multiple) {
                    selected.remove(i)
                } else {
                    selected.add(i)
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .shadow(3.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        vote()
                    }
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
                    Text(
                        text = EmojiSyntakts.render(option.title, emojiMap, emptyMap()),
                        maxLines = 1,
                        modifier = Modifier
                            .basicMarquee()
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                RadioButton(
                    selected = voted,
                    onClick = {
                        vote()
                    },
                    modifier = Modifier
                        .weight(0.25f)
                        .size(16.dp)
                )
            }
        }

        OutlinedButton(
            onClick = {
                onVote(poll.id, selected)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(getString(R.string.action_vote))
        }
    }
}