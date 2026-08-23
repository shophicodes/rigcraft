package com.example.rigcraft.util

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto

object MockDataSeeder {
    val categories = listOf(

        // Categories
        CategoryDto(
            categoryId = "computer-hardware",
            name = "Computer Hardware",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/Radeon™ RX 9060 XT GAMING OC 16G_11.png"
        ),

        // Subcategories
        CategoryDto(
            categoryId = "cpus",
            name = "Processors",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/cpus/image67496b288bb98.jpg.webp",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "motherboards",
            name = "Motherboards",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/motherboards/h1470.png",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "gpus",
            name = "Graphics Cards",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/graphics-cards/11348_01_rx9070xt_nitro_c02.png",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "ram",
            name = "Memory (RAM)",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/ram/FURY_Beast_Black_DDR5_2_angle-zm-lg.webp",
            parentCategory = "computer-hardware"
        ),
    )

    val products = listOf(
        // Processors
        ProductDto(
            id = "amd-ryzen-7-9800x3d",
            title = "AMD Ryzen 7 9800X3D",
            brand = "AMD",
            categoryId = "computer-hardware",
            subcategoryId = "cpus",
            price = 479.0,
            inStock = true,
            stockQuantity = 50,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/cpus/amd-ryzen-7-9800x3d/image67496b288bb98.jpg.webp"
            ),
            specifications = mapOf(
                "Cores" to "8",
                "Threads" to "16",
                "Base Clock" to "4.7 GHz",
                "Boost Clock" to "5.2 GHz",
                "L3 Cache" to "96 MB",
                "TDP" to "120 W",
                "Socket" to "AM5"
            )
        ),
        ProductDto(
            id = "intel-core-ultra-7-265k",
            title = "Intel Core Ultra 7 265K",
            brand = "Intel",
            categoryId = "computer-hardware",
            subcategoryId = "cpus",
            price = 399.0,
            inStock = true,
            stockQuantity = 30,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/cpus/intel-core-ultra-7-265k/image673dc9b75b22c.jpg.webp"
            ),
            specifications = mapOf(
                "Cores" to "20 (8P + 12E)",
                "Threads" to "20",
                "Base Clock" to "3.9 GHz",
                "Boost Clock" to "5.5 GHz",
                "L3 Cache" to "30 MB",
                "TDP" to "125 W",
                "Socket" to "LGA 1851"
            )
        ),
        ProductDto(
            id = "amd-ryzen-5-9600x",
            title = "AMD Ryzen 5 9600X",
            brand = "AMD",
            categoryId = "computer-hardware",
            subcategoryId = "cpus",
            price = 279.0,
            inStock = true,
            stockQuantity = 100,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/cpus/amd-ryzen-5-9600x/amd-ryzen-5-9600x-6-cores-do-5-4ghz-wof-procesor-cene.webp"
            ),
            specifications = mapOf(
                "Cores" to "6",
                "Threads" to "12",
                "Base Clock" to "3.9 GHz",
                "Boost Clock" to "5.4 GHz",
                "L3 Cache" to "32 MB",
                "TDP" to "65 W",
                "Socket" to "AM5"
            )
        ),

        // Motherboards
        ProductDto(
            id = "asus-rog-crosshair-x870e-hero",
            title = "ASUS ROG CROSSHAIR X870E HERO",
            brand = "ASUS",
            categoryId = "computer-hardware",
            subcategoryId = "motherboards",
            price = 699.0,
            inStock = true,
            stockQuantity = 15,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (1).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (2).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (3).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (4).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (5).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (6).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (7).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (8).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (9).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (10).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (11).png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/asus-rog-crosshair-x870e-hero/h1470 (12).png",
            ),
            specifications = mapOf(
                "Chipset" to "AMD X870E",
                "Socket" to "AM5",
                "Form Factor" to "ATX",
                "Memory Slots" to "4x DDR5",
                "Max Memory" to "192 GB",
                "PCIe Slots" to "2x PCIe 5.0 x16",
                "M.2 Slots" to "5x M.2"
            )
        ),
        ProductDto(
            id = "gigabyte-z890-aorus-elite-wifi7",
            title = "Gigabyte Z890 AORUS ELITE WIFI7",
            brand = "Gigabyte",
            categoryId = "computer-hardware",
            subcategoryId = "motherboards",
            price = 289.0,
            inStock = true,
            stockQuantity = 40,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/gigabyte-z890-aorus-elite-wifi7/Z890 AORUS ELITE WIFI7_02.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/gigabyte-z890-aorus-elite-wifi7/Z890 AORUS ELITE WIFI7_03.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/gigabyte-z890-aorus-elite-wifi7/Z890 AORUS ELITE WIFI7_04.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/gigabyte-z890-aorus-elite-wifi7/Z890 AORUS ELITE WIFI7_05.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/gigabyte-z890-aorus-elite-wifi7/Z890 AORUS ELITE WIFI7_06.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/gigabyte-z890-aorus-elite-wifi7/Z890 AORUS ELITE WIFI7_01.png",
            ),
            specifications = mapOf(
                "Chipset" to "Intel Z890",
                "Socket" to "LGA 1851",
                "Form Factor" to "ATX",
                "Memory Slots" to "4x DDR5",
                "Max Memory" to "256 GB",
                "PCIe Slots" to "1x PCIe 5.0 x16",
                "M.2 Slots" to "4x M.2"
            )
        ),
        ProductDto(
            id = "msi-mag-b850-tomahawk-max-wifi",
            title = "MSI MAG B850 TOMAHAWK MAX WIFI",
            brand = "MSI",
            categoryId = "computer-hardware",
            subcategoryId = "motherboards",
            price = 219.0,
            inStock = true,
            stockQuantity = 60,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/msi-mag-b850-tomahawk-max-wifi/product_1736159752859b0d59d04f539fee0f53b5742d9275.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/msi-mag-b850-tomahawk-max-wifi/product_1736159752ecbe58fc0b17cab0844af3fc70a5b3f8.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/msi-mag-b850-tomahawk-max-wifi/product_17361597543fdfe6a1d8d8f354948471e4d44da6f6.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/msi-mag-b850-tomahawk-max-wifi/product_1736159756c3f649510af0ca14f95cec03c009aab0.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/motherboards/msi-mag-b850-tomahawk-max-wifi/product_1736159754f30751ff77a834ba28009a222071b3d3.webp",
            ),
            specifications = mapOf(
                "Chipset" to "AMD B850",
                "Socket" to "AM5",
                "Form Factor" to "ATX",
                "Memory Slots" to "4x DDR5",
                "Max Memory" to "256 GB",
                "PCIe Slots" to "1x PCIe 5.0 x16, 2x PCIe 4.0 x16",
                "M.2 Slots" to "3x M.2"
            )
        ),

        // Graphics Cards
        ProductDto(
            id = "sapphire-nitro-amd-radeon-rx-9070-xt",
            title = "Sapphire NITRO+ AMD Radeon RX 9070 XT",
            brand = "Sapphire",
            categoryId = "computer-hardware",
            subcategoryId = "gpus",
            price = 599.0,
            inStock = true,
            stockQuantity = 25,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/RX9070XT_NITRO_Full_Box_Card.jpg",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/11348_01_rx9070xt_nitro_c01.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/11348_01_rx9070xt_nitro_c04.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/11348_01_rx9070xt_nitro_c03.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/11348_01_rx9070xt_nitro_c05.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/11348_01_rx9070xt_nitro_c02.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/sapphire-nitro-amd-radeon-rx-9070-xt/RX9070XT_NITRO_Full_Box.jpg",
            ),
            specifications = mapOf(
                "VRAM Capacity" to "16 GB",
                "VRAM Type" to "GDDR7",
                "Bus Width" to "256-bit",
                "HDMI Outputs" to "2",
                "DisplayPort Outputs" to "2",
                "Interface" to "PCIe 5.0 x16",
                "Length" to "320 mm"
            )
        ),
        ProductDto(
            id = "msi-geforce-rtx-5070-ti-16g-gaming-trio-oc",
            title = "MSI GeForce RTX 5070 Ti 16G GAMING TRIO OC",
            brand = "MSI",
            categoryId = "computer-hardware",
            subcategoryId = "gpus",
            price = 799.0,
            inStock = true,
            stockQuantity = 20,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_1738567494b5a1a4c58600a87f33be548bb80e081d.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_1736215173706f39033b3637e62fb97d9eb58dac03.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_173621438880fb1c6c6479b8fb2a87f73b3f64bf64.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_1741828482c9856b6258f7c5725994eda593d94787.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_17418284828c4cf10bfdb1c187b757fab713bf5284.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_1736215195ec621294cb5fd016ba620298e6c26dd2.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/msi-geforce-rtx-5070-ti-16g-gaming-trio-oc/product_1736215195c745fbb8ccfea6001635337c7e0733e9.png",
            ),
            specifications = mapOf(
                "VRAM Capacity" to "16 GB",
                "VRAM Type" to "GDDR7",
                "Bus Width" to "256-bit",
                "HDMI Outputs" to "1",
                "DisplayPort Outputs" to "3",
                "Interface" to "PCIe 5.0 x16",
                "Length" to "337 mm"
            )
        ),
        ProductDto(
            id = "gigabyte-radeon-rx-9060-xt-gaming-oc",
            title = "Gigabyte Radeon RX 9060 XT Gaming OC",
            brand = "Gigabyte",
            categoryId = "computer-hardware",
            subcategoryId = "gpus",
            price = 349.0,
            inStock = true,
            stockQuantity = 45,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_01.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_02.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_11.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_06.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_07.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_09.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_04.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_05.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_10.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_08.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/graphics-cards/gigabyte-radeon-rx-9060-xt-gaming-oc/Radeon™ RX 9060 XT GAMING OC 16G_03.png",
            ),
            specifications = mapOf(
                "VRAM Capacity" to "12 GB",
                "VRAM Type" to "GDDR7",
                "Bus Width" to "192-bit",
                "HDMI Outputs" to "2",
                "DisplayPort Outputs" to "2",
                "Interface" to "PCIe 5.0 x16",
                "Length" to "282 mm"
            )
        ),

        // Memory (RAM)
        ProductDto(
            id = "corsair-vengeance-rgb-32gb-ddr5-6000-cl30",
            title = "Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz CL30",
            brand = "Corsair",
            categoryId = "computer-hardware",
            subcategoryId = "ram",
            price = 119.0,
            inStock = true,
            stockQuantity = 80,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/kingston-fury-beast-64gb-ddr5-6400-cl32/FURY_Beast_Black_DDR5_2_angle-zm-lg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/kingston-fury-beast-64gb-ddr5-6400-cl32/FURY_Beast_Black_DDR5_1-zm-lg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/kingston-fury-beast-64gb-ddr5-6400-cl32/FURY_Beast_Black_DDR5_2_pkg-zm-lg.jpg",
            ),
            specifications = mapOf(
                "Capacity" to "32 GB",
                "Speed" to "6000 MHz",
                "Latency" to "CL30",
                "Type" to "DDR5",
                "Modules" to "2 x 16 GB"
            )
        ),
        ProductDto(
            id = "kingston-fury-beast-64gb-ddr5-6400-cl32",
            title = "Kingston FURY Beast 64GB (2x32GB) DD5 6400MHz CL32",
            brand = "Kingston",
            categoryId = "computer-hardware",
            subcategoryId = "ram",
            price = 209.0,
            inStock = true,
            stockQuantity = 35,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/corsair-vengeance-rgb-32gb-ddr5-6000-cl30/VENGEANCE_RGB_DDR5_BLK_01.avif",
            ),
            specifications = mapOf(
                "Capacity" to "64 GB",
                "Speed" to "6400 MHz",
                "Latency" to "CL32",
                "Type" to "DDR5",
                "Modules" to "2 x 32 GB"
            )
        ),
    )
}