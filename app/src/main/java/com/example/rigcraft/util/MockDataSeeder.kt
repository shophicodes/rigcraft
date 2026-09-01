package com.example.rigcraft.util

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto

object MockDataSeeder {
    val categories = listOf(

        // Kategorije
        CategoryDto(
            categoryId = "computer-hardware",
            name = "Računarske Komponente",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/Radeon™ RX 9060 XT GAMING OC 16G_11.png"
        ),
        CategoryDto(
            categoryId = "computer-peripherals",
            name = "Računarske Periferije",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-peripherals/image6750115cd5450.jpg.webp"
        ),
        CategoryDto(
            categoryId = "external-storage",
            name = "Eksterno Skladište",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/external-storage/74061732637602.jpg.webp"
        ),

        // Podkategorije
        CategoryDto(
            categoryId = "cpus",
            name = "Procesori",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/cpus/image67496b288bb98.jpg.webp",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "motherboards",
            name = "Matične ploče",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/motherboards/h1470.png",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "gpus",
            name = "Grafičke karte",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/graphics-cards/11348_01_rx9070xt_nitro_c02.png",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "ram",
            name = "RAM Memorije",
            imageUrl = "https://ik.imagekit.io/rel0t282g/tr:w-500,f-auto/rigcraft/products/computer-hardware/ram/FURY_Beast_Black_DDR5_2_angle-zm-lg.webp",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "hard-disks",
            name = "Hard Diskovi",
            imageUrl = "",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "ssd",
            name = "SSD Diskovi",
            imageUrl = "",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "power-supplies",
            name = "Napajanja",
            imageUrl = "",
            parentCategory = "computer-hardware"
        ),
        CategoryDto(
            categoryId = "monitors",
            name = "Monitori",
            imageUrl = "",
            parentCategory = "computer-peripherals"
        ),
        CategoryDto(
            categoryId = "keyboards",
            name = "Tastature",
            imageUrl = "",
            parentCategory = "computer-peripherals"
        ),
        CategoryDto(
            categoryId = "mouses",
            name = "Miševi",
            imageUrl = "",
            parentCategory = "computer-peripherals"
        ),
        CategoryDto(
            categoryId = "usb-flash-drives",
            name = "USB fleš memorije",
            imageUrl = "",
            parentCategory = "external-storage"
        ),
        CategoryDto(
            categoryId = "external-hdd",
            name = "Eksterni hard diskovi",
            imageUrl = "",
            parentCategory = "external-storage"
        ),
    )

    val products = listOf(
        // Procesori
        ProductDto(
            id = "amd-ryzen-7-9800x3d",
            title = "AMD Ryzen 7 9800X3D",
            brand = "AMD",
            categoryId = "computer-hardware",
            subcategoryId = "cpus",
            price = 40000.0,
            inStock = true,
            stockQuantity = 50,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/cpus/amd-ryzen-7-9800x3d/image67496b288bb98.jpg.webp"
            ),
            specifications = mapOf(
                "Broj jezgara" to "8",
                "Broj niti" to "16",
                "Osnovna frekvencija" to "4.7 GHz",
                "Turbo frekvencija" to "5.2 GHz",
                "L3 keš memorija" to "96 MB",
                "TDP" to "120 W",
                "Podnožje" to "AM5"
            )
        ),
        ProductDto(
            id = "intel-core-ultra-7-265k",
            title = "Intel Core Ultra 7 265K",
            brand = "Intel",
            categoryId = "computer-hardware",
            subcategoryId = "cpus",
            price = 30000.0,
            inStock = true,
            stockQuantity = 30,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/cpus/intel-core-ultra-7-265k/image673dc9b75b22c.jpg.webp"
            ),
            specifications = mapOf(
                "Broj jezgara" to "20 (8P + 12E)",
                "Broj niti" to "20",
                "Osnovna frekvencija" to "3.9 GHz",
                "Turbo frekvencija" to "5.5 GHz",
                "L3 keš memorija" to "30 MB",
                "TDP" to "125 W",
                "Podnožje" to "LGA 1851"
            )
        ),
        ProductDto(
            id = "amd-ryzen-5-9600x",
            title = "AMD Ryzen 5 9600X",
            brand = "AMD",
            categoryId = "computer-hardware",
            subcategoryId = "cpus",
            price = 20000.0,
            inStock = true,
            stockQuantity = 100,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/cpus/amd-ryzen-5-9600x/amd-ryzen-5-9600x-6-cores-do-5-4ghz-wof-procesor-cene.webp"
            ),
            specifications = mapOf(
                "Broj jezgara" to "6",
                "Broj niti" to "12",
                "Osnovna frekvencija" to "3.9 GHz",
                "Turbo frekvencija" to "5.4 GHz",
                "L3 keš memorija" to "32 MB",
                "TDP" to "65 W",
                "Podnožje" to "AM5"
            )
        ),

        // Matične ploče
        ProductDto(
            id = "asus-rog-crosshair-x870e-hero",
            title = "ASUS ROG CROSSHAIR X870E HERO",
            brand = "ASUS",
            categoryId = "computer-hardware",
            subcategoryId = "motherboards",
            price = 70000.0,
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
                "Čipset" to "AMD X870E",
                "Podnožje" to "AM5",
                "Format ploče" to "ATX",
                "Broj RAM slotova" to "4x DDR5",
                "Maksimalno podržana memorija" to "192 GB",
                "Broj PCIe slotova" to "2x PCIe 5.0 x16",
                "Broj M.2 slotova" to "5x M.2"
            )
        ),
        ProductDto(
            id = "gigabyte-z890-aorus-elite-wifi7",
            title = "Gigabyte Z890 AORUS ELITE WIFI7",
            brand = "Gigabyte",
            categoryId = "computer-hardware",
            subcategoryId = "motherboards",
            price = 30000.0,
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
                "Čipset" to "Intel Z890",
                "Podnožje" to "LGA 1851",
                "Format ploče" to "ATX",
                "Broj RAM slotova" to "4x DDR5",
                "Maksimalno podržana memorija" to "256 GB",
                "Broj PCIe slotova" to "1x PCIe 5.0 x16",
                "Broj M.2 slotova" to "4x M.2"
            )
        ),
        ProductDto(
            id = "msi-mag-b850-tomahawk-max-wifi",
            title = "MSI MAG B850 TOMAHAWK MAX WIFI",
            brand = "MSI",
            categoryId = "computer-hardware",
            subcategoryId = "motherboards",
            price = 25000.0,
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
                "Čipset" to "AMD B850",
                "Podnožje" to "AM5",
                "Format ploče" to "ATX",
                "Broj RAM slotova" to "4x DDR5",
                "Maksimalno podržana memorija" to "256 GB",
                "Broj PCIe slotova" to "1x PCIe 5.0 x16, 2x PCIe 4.0 x16",
                "Broj M.2 slotova" to "3x M.2"
            )
        ),

        // Grafičke karte
        ProductDto(
            id = "sapphire-nitro-amd-radeon-rx-9070-xt",
            title = "Sapphire NITRO+ AMD Radeon RX 9070 XT",
            brand = "Sapphire",
            categoryId = "computer-hardware",
            subcategoryId = "gpus",
            price = 80000.0,
            discountPercent = 15,
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
                "Količina memorije" to "16 GB",
                "Tip memorije" to "GDDR7",
                "Magistrala memorije" to "256-bit",
                "Broj HDMI izlaza" to "2",
                "Broj DisplayPort izlaza" to "2",
                "Interfejs" to "PCIe 5.0 x16",
                "Dužina" to "320 mm"
            )
        ),
        ProductDto(
            id = "msi-geforce-rtx-5070-ti-16g-gaming-trio-oc",
            title = "MSI GeForce RTX 5070 Ti 16G GAMING TRIO OC",
            brand = "MSI",
            categoryId = "computer-hardware",
            subcategoryId = "gpus",
            price = 80000.0,
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
                "Količina memorije" to "16 GB",
                "Tip memorije" to "GDDR7",
                "Magistrala memorije" to "256-bit",
                "Broj HDMI izlaza" to "1",
                "Broj DisplayPort izlaza" to "3",
                "Interfejs" to "PCIe 5.0 x16",
                "Dužina" to "337 mm"
            )
        ),
        ProductDto(
            id = "gigabyte-radeon-rx-9060-xt-gaming-oc",
            title = "Gigabyte Radeon RX 9060 XT Gaming OC",
            brand = "Gigabyte",
            categoryId = "computer-hardware",
            subcategoryId = "gpus",
            price = 40000.0,
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
                "Količina memorije" to "12 GB",
                "Tip memorije" to "GDDR7",
                "Magistrala memorije" to "192-bit",
                "Broj DisplayPort izlaza" to "2",
                "DisplayPort Outputs" to "2",
                "Interfejs" to "PCIe 5.0 x16",
                "Dužina" to "282 mm"
            )
        ),

        // RAM memorija
        ProductDto(
            id = "corsair-vengeance-rgb-32gb-ddr5-6000-cl30",
            title = "Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz CL30",
            brand = "Corsair",
            categoryId = "computer-hardware",
            subcategoryId = "ram",
            price = 50000.0,
            inStock = true,
            stockQuantity = 80,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/corsair-vengeance-rgb-32gb-ddr5-6000-cl30/VENGEANCE_RGB_DDR5_BLK_01.avif",
            ),
            specifications = mapOf(
                "Kapacitet" to "32 GB",
                "Brzina" to "6000 MHz",
                "Latencija" to "CL30",
                "Tip" to "DDR5",
                "Moduli" to "2 x 16 GB"
            )
        ),
        ProductDto(
            id = "kingston-fury-beast-64gb-ddr5-6400-cl32",
            title = "Kingston FURY Beast 64GB (2x32GB) DD5 6400MHz CL32",
            brand = "Kingston",
            categoryId = "computer-hardware",
            subcategoryId = "ram",
            price = 100000.0,
            inStock = true,
            stockQuantity = 35,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/kingston-fury-beast-64gb-ddr5-6400-cl32/FURY_Beast_Black_DDR5_2_angle-zm-lg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/kingston-fury-beast-64gb-ddr5-6400-cl32/FURY_Beast_Black_DDR5_1-zm-lg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ram/kingston-fury-beast-64gb-ddr5-6400-cl32/FURY_Beast_Black_DDR5_2_pkg-zm-lg.jpg",
            ),
            specifications = mapOf(
                "Kapacitet" to "64 GB",
                "Brzina" to "6400 MHz",
                "Latencija" to "CL32",
                "Tip" to "DDR5",
                "Moduli" to "2 x 32 GB"
            )
        ),
        // Hard Diskovi
        ProductDto(
            id = "wd-blue-2tb-sata3",
            title = "WD Blue 2TB SATA III 3.5'' WD20ERAZ HDD",
            brand = "WD",
            categoryId = "computer-hardware",
            subcategoryId = "hard-disks",
            price = 7500.0,
            inStock = true,
            stockQuantity = 40,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/hard-disks/wd-blue-2tb-sata3/image6571a023c9b10.jpg.webp"
            ),
            specifications = mapOf(
                "Kapacitet" to "2 TB",
                "Interfejs" to "SATA III",
                "Format" to "3.5\"",
                "Brzina obrtaja" to "5400 RPM",
                "Keš memorija" to "256 MB"
            )
        ),
        ProductDto(
            id = "seagate-ironwolf-pro-8tb-sata3",
            title = "SEAGATE IronWolf Pro 8TB SATA III 3.5'",
            brand = "Seagate",
            categoryId = "computer-hardware",
            subcategoryId = "hard-disks",
            price = 28000.0,
            inStock = true,
            stockQuantity = 15,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/hard-disks/seagate-ironwolf-pro-8tb-sata3/image655f41053846d_1.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/hard-disks/seagate-ironwolf-pro-8tb-sata3/image655f4105b7dbd_1.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "8 TB",
                "Interfejs" to "SATA III",
                "Format" to "3.5\"",
                "Brzina obrtaja" to "7200 RPM",
                "Keš memorija" to "256 MB"
            )
        ),
        // SSD Diskovi
        ProductDto(
            id = "samsung-990-pro-2tb-pcie-nvme-m2",
            title = "SAMSUNG 990 PRO 2TB PCIe NVMe M.2 MZ-V9P2T0BW",
            brand = "Samsung",
            categoryId = "computer-hardware",
            subcategoryId = "ssd",
            price = 24000.0,
            inStock = true,
            stockQuantity = 25,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/samsung-990-pro-2tb-pcie-nvme-m2/image638efc9575374.png.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/samsung-990-pro-2tb-pcie-nvme-m2/image638efc96de49d.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/samsung-990-pro-2tb-pcie-nvme-m2/image638efc9678d0d.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/samsung-990-pro-2tb-pcie-nvme-m2/image638efc95eec35.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "2 TB",
                "Interfejs" to "PCIe Gen 4.0 x4",
                "Format" to "M.2 2280",
                "Brzina čitanja" to "do 7450 MB/s",
                "Brzina pisanja" to "do 6900 MB/s"
            )
        ),
        ProductDto(
            id = "adata-legend-710-512gb-pcie-m2",
            title = "A-DATA LEGEND 710 512GB PCIe M.2 ALEG-710-1TCS",
            brand = "A-DATA",
            categoryId = "computer-hardware",
            subcategoryId = "ssd",
            price = 5500.0,
            inStock = true,
            stockQuantity = 50,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/adata-legend-710-512gb-pcie-m2/image63971bb9a4489.png.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/adata-legend-710-512gb-pcie-m2/image63971bbab4321.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/adata-legend-710-512gb-pcie-m2/image63971bba3dabd.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "512 GB",
                "Interfejs" to "PCIe Gen 3.0 x4",
                "Format" to "M.2 2280",
                "Brzina čitanja" to "do 2400 MB/s",
                "Brzina pisanja" to "do 1800 MB/s"
            )
        ),
        ProductDto(
            id = "crucial-bx500-240gb-sata3",
            title = "CRUCIAL SSD 240GB BX500 CT240BX500SSD1",
            brand = "Crucial",
            categoryId = "computer-hardware",
            subcategoryId = "ssd",
            price = 3000.0,
            inStock = true,
            stockQuantity = 60,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/crucial-bx500-240gb-sata3/image5bd85d23db3d1.png.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/crucial-bx500-240gb-sata3/image5bd85d2540f56.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/crucial-bx500-240gb-sata3/image5bd85d22e580d.png.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/ssd/crucial-bx500-240gb-sata3/image5bd85d26bb61c.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "240 GB",
                "Interfejs" to "SATA III",
                "Format" to "2.5\"",
                "Brzina čitanja" to "do 540 MB/s",
                "Brzina pisanja" to "do 500 MB/s"
            )
        ),
        // Napajanja
        ProductDto(
            id = "msi-mag-a650bn-650w",
            title = "MSI MAG A650BN",
            brand = "MSI",
            categoryId = "computer-hardware",
            subcategoryId = "power-supplies",
            price = 8500.0,
            inStock = true,
            stockQuantity = 30,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/msi-mag-a650bn-650w/image6256c0e81d40c.png.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/msi-mag-a650bn-650w/image6256c0f512723.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/msi-mag-a650bn-650w/image6256c0fac326d.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/msi-mag-a650bn-650w/image6256c0eecae62.jpg.webp",
            ),
            specifications = mapOf(
                "Snaga" to "650 W",
                "Sertifikat" to "80 Plus Bronze",
                "Format" to "ATX",
                "Hlađenje" to "120mm ventilator"
            )
        ),
        ProductDto(
            id = "corsair-rm850e-850w",
            title = "CORSAIR RM850e",
            brand = "Corsair",
            categoryId = "computer-hardware",
            subcategoryId = "power-supplies",
            price = 16000.0,
            discountPercent = 15,
            inStock = true,
            stockQuantity = 20,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/corsair-rm850e-850w/84000669120401.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/corsair-rm850e-850w/84000669120402.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-hardware/power-supplies/corsair-rm850e-850w/84000669120403.jpg.webp",
            ),
            specifications = mapOf(
                "Snaga" to "850 W",
                "Sertifikat" to "80 Plus Gold",
                "Modularno" to "Potpuno modularno",
                "Format" to "ATX"
            )
        ),
        // Monitori
        ProductDto(
            id = "gigabyte-24-ips-gs24f14",
            title = "GIGABYTE 23.8\" IPS GS24F14",
            brand = "Gigabyte",
            categoryId = "computer-peripherals",
            subcategoryId = "monitors",
            price = 18000.0,
            inStock = true,
            stockQuantity = 15,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/gigabyte-24-ips-gs24f14/4719331877217-6.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/gigabyte-24-ips-gs24f14/4719331877217-5.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/gigabyte-24-ips-gs24f14/4719331877217-3.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/gigabyte-24-ips-gs24f14/4719331877217-4.webp",
            ),
            specifications = mapOf(
                "Dijagonala" to "23.8\"",
                "Tip panela" to "IPS",
                "Rezolucija" to "1920 x 1080",
                "Osvežavanje" to "170 Hz (OC)",
                "Vreme odziva" to "1 ms"
            )
        ),
        ProductDto(
            id = "msi-mag-27-va-275cf-x24",
            title = "MSI MAG 27\" VA 275CF X24",
            brand = "MSI",
            categoryId = "computer-peripherals",
            subcategoryId = "monitors",
            price = 22000.0,
            inStock = true,
            stockQuantity = 10,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/msi-mag-27-va-275cf-x24/4711377338899.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/msi-mag-27-va-275cf-x24/4711377338899_1_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/msi-mag-27-va-275cf-x24/4711377338899_2_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/msi-mag-27-va-275cf-x24/4711377338899_4_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/msi-mag-27-va-275cf-x24/4711377338899_3_.jpg.webp",
            ),
            specifications = mapOf(
                "Dijagonala" to "27\"",
                "Tip panela" to "VA",
                "Zakrivljenost" to "1500R",
                "Rezolucija" to "1920 x 1080",
                "Osvežavanje" to "100 Hz"
            )
        ),
        ProductDto(
            id = "samsung-odyssey-g6-27-qd-oled-ls27hg612suxen",
            title = "SAMSUNG Odyssey G6 27'' QD-OLED LS27HG612SUXEN",
            brand = "Samsung",
            categoryId = "computer-peripherals",
            subcategoryId = "monitors",
            price = 110000.0,
            inStock = true,
            stockQuantity = 5,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/samsung-odyssey-g6-27-qd-oled-ls27hg612suxen/8806097910381-2.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/samsung-odyssey-g6-27-qd-oled-ls27hg612suxen/8806097910381-3.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/samsung-odyssey-g6-27-qd-oled-ls27hg612suxen/8806097910381-0.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/samsung-odyssey-g6-27-qd-oled-ls27hg612suxen/8806097910381-1.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/samsung-odyssey-g6-27-qd-oled-ls27hg612suxen/8806097910381-11.webp",
            ),
            specifications = mapOf(
                "Dijagonala" to "27\"",
                "Tip panela" to "QD-OLED",
                "Rezolucija" to "2560 x 1440",
                "Osvežavanje" to "360 Hz",
                "Vreme odziva" to "0.03 ms"
            )
        ),
        ProductDto(
            id = "dell-pro-27-ips-p2725qe",
            title = "DELL Pro 27\" IPS P2725QE",
            brand = "Dell",
            categoryId = "computer-peripherals",
            subcategoryId = "monitors",
            price = 65000.0,
            inStock = true,
            stockQuantity = 8,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/dell-pro-27-ips-p2725qe/monitor-p2725qe-pro-plus-c-black-gallery-2.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/dell-pro-27-ips-p2725qe/monitor-p2725qe-pro-plus-c-black-gallery-3.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/dell-pro-27-ips-p2725qe/monitor-p2725qe-pro-plus-c-black-gallery-1.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/dell-pro-27-ips-p2725qe/monitor-p2725qe-pro-plus-c-black-gallery-9.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/monitors/dell-pro-27-ips-p2725qe/monitor-p2725qe-pro-plus-c-black-gallery-6.jpg.webp",
            ),
            specifications = mapOf(
                "Dijagonala" to "27\"",
                "Tip panela" to "IPS",
                "Rezolucija" to "3840 x 2160 (4K)",
                "Osvežavanje" to "60 Hz",
                "Povezivanje" to "USB-C, HDMI, DP"
            )
        ),
        // Tastature
        ProductDto(
            id = "redragon-kumara-k552-rgb-black",
            title = "REDRAGON Kumara K552 RGB Black",
            brand = "Redragon",
            categoryId = "computer-peripherals",
            subcategoryId = "keyboards",
            price = 5500.0,
            inStock = true,
            stockQuantity = 20,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/redragon-kumara-k552-rgb-black/image6750115cd5450.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/redragon-kumara-k552-rgb-black/image6750115d34c6d.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/redragon-kumara-k552-rgb-black/image6750115dc5b09.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/redragon-kumara-k552-rgb-black/image6750115d7b871.jpg.webp",
            ),
            specifications = mapOf(
                "Tip" to "Mehanička",
                "Prekidači" to "Blue switches",
                "Osvetljenje" to "RGB",
                "Povezivanje" to "USB"
            )
        ),
        ProductDto(
            id = "royal-kludge-s98-brown-switch-cloud-us",
            title = "ROYAL KLUDGE S98 Brown Switch Cloud US",
            brand = "Royal Kludge",
            categoryId = "computer-peripherals",
            subcategoryId = "keyboards",
            price = 12000.0,
            inStock = true,
            stockQuantity = 12,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/royal-kludge-s98-brown-switch-cloud-us/6935280823800_1.jpg.webp",
            ),
            specifications = mapOf(
                "Tip" to "Mehanička",
                "Prekidači" to "Brown switches",
                "Povezivanje" to "USB, 2.4G, BT",
                "Osvetljenje" to "RGB"
            )
        ),
        ProductDto(
            id = "marvo-k604-soldat-20-black",
            title = "MARVO K604 Soldat 20 Black",
            brand = "Marvo",
            categoryId = "computer-peripherals",
            subcategoryId = "keyboards",
            price = 1500.0,
            inStock = true,
            stockQuantity = 50,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/marvo-k604-soldat-20-black/image675036a146d2e.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/marvo-k604-soldat-20-black/image675036a240a9f.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/marvo-k604-soldat-20-black/image675036a1e8be9.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/marvo-k604-soldat-20-black/image675036a19d71b.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/keyboards/marvo-k604-soldat-20-black/image675036a29696c.jpg.webp",
            ),
            specifications = mapOf(
                "Tip" to "Membranska",
                "Osvetljenje" to "Rainbow",
                "Povezivanje" to "USB"
            )
        ),
        // Miševi
        ProductDto(
            id = "steelseries-aerox-onyx-3-black",
            title = "STEELSERIES Aerox Onyx 3 Black",
            brand = "SteelSeries",
            categoryId = "computer-peripherals",
            subcategoryId = "mouses",
            price = 6000.0,
            inStock = true,
            stockQuantity = 25,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/steelseries-aerox-onyx-3-black/5707119066266-2--1-.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/steelseries-aerox-onyx-3-black/5707119066266-0.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/steelseries-aerox-onyx-3-black/5707119066266-1.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/steelseries-aerox-onyx-3-black/mis-1.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/steelseries-aerox-onyx-3-black/mis-0.webp",
            ),
            specifications = mapOf(
                "Senzor" to "Optički",
                "Rezolucija" to "8500 DPI",
                "Povezivanje" to "USB",
                "Težina" to "59g"
            )
        ),
        ProductDto(
            id = "marvo-m803w-bk-black",
            title = "MARVO M803W BK Black",
            brand = "Marvo",
            categoryId = "computer-peripherals",
            subcategoryId = "mouses",
            price = 2500.0,
            discountPercent = 25,
            inStock = true,
            stockQuantity = 35,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/marvo-m803w-bk-black/image66a8f1559281d.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/marvo-m803w-bk-black/image66a8f15692fc2.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/marvo-m803w-bk-black/image66a8f1564740b.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/marvo-m803w-bk-black/image66a8f155e6dc0.jpg.webp",
            ),
            specifications = mapOf(
                "Povezivanje" to "Bežično",
                "Rezolucija" to "do 4800 DPI",
                "Osvetljenje" to "RGB"
            )
        ),
        ProductDto(
            id = "asus-rog-gladius-iii-core",
            title = "ASUS ROG Gladius III Core",
            brand = "ASUS",
            categoryId = "computer-peripherals",
            subcategoryId = "mouses",
            price = 7000.0,
            inStock = true,
            stockQuantity = 20,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/asus-rog-gladius-iii-core/4711636014014_2_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/asus-rog-gladius-iii-core/4711636014014_5_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/asus-rog-gladius-iii-core/4711636014014_3_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/asus-rog-gladius-iii-core/4711636014014_4_.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/computer-peripherals/mouses/asus-rog-gladius-iii-core/4711636014014_1_.jpg.webp",
            ),
            specifications = mapOf(
                "Senzor" to "Optički",
                "Rezolucija" to "do 19000 DPI",
                "Povezivanje" to "USB",
                "Tasteri" to "6 programabilnih"
            )
        ),
        // USB fleš memorije
        ProductDto(
            id = "kingston-datatraveler-exodia-m-128gb",
            title = "KINGSTON DataTraveler Exodia M USB 128 GB",
            brand = "Kingston",
            categoryId = "external-storage",
            subcategoryId = "usb-flash-drives",
            price = 1500.0,
            inStock = true,
            stockQuantity = 100,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/usb-flash-drives/kingston-datatraveler-exodia-m-128gb/74061732637602.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/usb-flash-drives/kingston-datatraveler-exodia-m-128gb/74061732637601.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "128 GB",
                "Interfejs" to "USB 3.2 Gen 1"
            )
        ),
        ProductDto(
            id = "kingston-dt70-64gb",
            title = "KINGSTON DT70/64GB",
            brand = "Kingston",
            categoryId = "external-storage",
            subcategoryId = "usb-flash-drives",
            price = 1000.0,
            inStock = true,
            stockQuantity = 150,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/usb-flash-drives/kingston-dt70-64gb/image5ef9f4874d090.png.png",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/usb-flash-drives/kingston-dt70-64gb/image5ef9f4854b9bf.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/usb-flash-drives/kingston-dt70-64gb/image5ef9f48650bd1.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "64 GB",
                "Interfejs" to "USB-C 3.2 Gen 1"
            )
        ),
        // Eksterni hard diskovi
        ProductDto(
            id = "toshiba-canvio-ready-1tb-black-hdtp310ek3aa",
            title = "TOSHIBA Canvio Ready 1TB Black HDTP310EK3AA",
            brand = "Toshiba",
            categoryId = "external-storage",
            subcategoryId = "external-hdd",
            price = 6500.0,
            inStock = true,
            stockQuantity = 40,
            images = listOf(
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/external-hdd/toshiba-canvio-ready-1tb-black-hdtp310ek3aa/image65670f7546016.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/external-hdd/toshiba-canvio-ready-1tb-black-hdtp310ek3aa/image65670f75ac52c.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/external-hdd/toshiba-canvio-ready-1tb-black-hdtp310ek3aa/image65670f76114f1.jpg.webp",
                "https://ik.imagekit.io/rel0t282g/tr:w-800,h-800,q-80,f-auto/rigcraft/products/external-storage/external-hdd/toshiba-canvio-ready-1tb-black-hdtp310ek3aa/image65670f76724a3.jpg.webp",
            ),
            specifications = mapOf(
                "Kapacitet" to "1 TB",
                "Interfejs" to "USB 3.2 Gen 1",
                "Format" to "2.5\""
            )
        ),
    )
}