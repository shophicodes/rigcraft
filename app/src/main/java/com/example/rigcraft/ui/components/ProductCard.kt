package com.example.rigcraft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.rigcraft.R
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.ui.theme.RigCraftTheme

@Composable
fun ProductCard(
    product: ProductDto,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(dimensionResource(R.dimen.product_card_width))
            .clickable { onProductClick(product.id) },
        shape = RoundedCornerShape(dimensionResource(R.dimen.product_card_corner_radius)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.product_image_height))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )

                // Discount Badge if product is on sale
                if (product.discountPercent > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(Color(0xFFFF6B00), RoundedCornerShape(bottomEnd = dimensionResource(R.dimen.padding_small)))
                            .padding(
                                horizontal = dimensionResource(R.dimen.badge_padding_horizontal),
                                vertical = dimensionResource(R.dimen.badge_padding_vertical)
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.discount_format, product.discountPercent),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))

            // Pricing Row
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                val finalPrice = if (product.discountPercent > 0) {
                    product.price * (1 - product.discountPercent / 100.0)
                } else product.price

                Text(
                    text = stringResource(R.string.price_format, finalPrice, stringResource(R.string.currency_rsd)),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_small)))
                if (product.discountPercent > 0) {
                    Text(
                        text = stringResource(R.string.price_format, product.price, stringResource(R.string.currency_rsd)),
                        style = MaterialTheme.typography.labelSmall,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                }
                else {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    RigCraftTheme {
        ProductCard(
            product = ProductDto(),
            onProductClick = {}
        )
    }
}