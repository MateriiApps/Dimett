package xyz.wingio.dimett.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.utils.TabOptions
import xyz.wingio.dimett.utils.getString

// TODO: Implement
open class ProfileScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.img_thinking),
                contentDescription = stringResource(R.string.cd_thinking_emoji),
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = getString(R.string.msg_no_content),
                style = MaterialTheme.typography.labelLarge,
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
        }
    }

}

/**
 * Lets us use [ProfileScreen] as a tab
 */
class ProfileTab : ProfileScreen(), Tab {

    override val options: TabOptions
        @Composable get() = TabOptions(
            R.string.title_profile,
            icon = Icons.Outlined.Person,
            iconSelected = Icons.Filled.Person
        )

}