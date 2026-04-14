-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 10, 2026 at 09:36 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dweb`
--

-- --------------------------------------------------------

--
-- Table structure for table `contact_message`
--

CREATE TABLE `contact_message` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `content` text NOT NULL,
  `date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `contact_message`
--

INSERT INTO `contact_message` (`id`, `name`, `gender`, `phone`, `address`, `email`, `content`, `date`) VALUES
(1, '李軒雲', '男性', '', '工業一路60-2號6-1樓', '', '', NULL),
(2, '李軒雲', '男性', '', '工業一路60-2號6-1樓', '', '', NULL),
(3, '李軒雲', '男性', '', '工業一路60-2號6-1樓', '', '', NULL),
(4, '李軒雲', '男性', '', '工業一路60-2號6-1樓', '', '', NULL),
(5, '李軒雲', '男性', '', '工業一路60-2號6-1樓', '', '', '2025-10-31 16:17:08.000000'),
(6, '李軒', '男性', '5566', '工業一路60-2號6-1樓', 'blo@gmail.com', '56626262', '2025-10-31 16:20:57.000000'),
(7, '李雲', '男性', '', '工業一路60-2號6-1樓', '', '', '2025-10-31 16:37:53.000000'),
(8, '李軒雲', '女', '937589600', '工業一路60-2號6-1樓', 'bloodandjewelry@gmail.com', '511515', '2025-11-20 14:35:03.000000'),
(9, 'Jacob Li', '男', '31664', '', 'bloodandjewelry@gmail.com', '651651651651', '2026-04-02 15:51:28.000000');

-- --------------------------------------------------------

--
-- Table structure for table `download_file`
--

CREATE TABLE `download_file` (
  `id` bigint(20) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `file_size` varchar(255) DEFAULT NULL,
  `filename` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `update_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `download_file`
--

INSERT INTO `download_file` (`id`, `enabled`, `file_size`, `filename`, `title`, `update_date`) VALUES
(1, b'1', '53.4 MB', 'Σ-XS_類比電壓／脈波列指令型_中文.pdf', 'Omega-XS_類比電壓/脈波指令型', '2026-03-31'),
(2, b'1', '36.3 MB', 'GA500_中文手冊.pdf', '安川GA500操作手冊', '2026-03-31'),
(3, b'1', '35.9 MB', 'GA700中文手冊.pdf', '安川GA700操作手冊', '2026-03-31'),
(4, b'1', '25.1 MB', 'B3  使用手冊.pdf', '台達B3使用手冊', '2026-03-31'),
(5, b'1', '24.3 MB', 'MS300 使用手冊.pdf', '台達MS300使用手冊', '2026-03-31'),
(6, b'1', '72.4 MB', 'VGS電子目錄-1-5.pdf', 'VGS目錄第一段', '2026-03-31'),
(7, b'1', '58.4 MB', 'VGS電子目錄-6-9.pdf', 'VGS目錄第二段', '2026-03-31'),
(8, b'1', '74.8 MB', 'VGS電子目錄-10-14.pdf', 'VGS目錄第三段', '2026-03-31'),
(9, b'1', '71.3 MB', 'VGS電子目錄-15-19.pdf', 'VGS目錄第四段(最終)', '2026-03-31');

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `news_id` bigint(20) NOT NULL,
  `news_content` text NOT NULL,
  `news_send_mode` varchar(255) NOT NULL,
  `news_send_time` datetime(6) DEFAULT NULL,
  `news_status` varchar(255) NOT NULL,
  `news_title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`news_id`, `news_content`, `news_send_mode`, `news_send_time`, `news_status`, `news_title`) VALUES
(11, '2828', 'scheduled', '2026-01-30 15:53:00.000000', 'published', '2882'),
(12, '38388383', 'scheduled', '2026-01-30 15:57:00.000000', 'published', '383838383'),
(13, '測試第一次', 'immediate', '2026-04-02 15:39:41.000000', 'published', '倒數測試第一次'),
(14, '倒數測試第二次-', 'scheduled', '2026-04-02 15:42:00.000000', 'published', '倒數測試第二次');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `vendor_email` varchar(100) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `image_filename` varchar(255) DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `category`, `description`, `image_url`, `vendor_email`, `created_time`, `image_filename`, `stock`, `brand`, `code`, `spec`) VALUES
(2, '2', 26, NULL, '626626262626', NULL, NULL, '2025-11-14 08:20:23', '1763108422955_胺基酸.png', 23, NULL, NULL, NULL),
(4, '8', 26, NULL, '62162626', NULL, NULL, '2025-11-17 06:44:05', '1763361845120_6.png', 216, NULL, NULL, NULL),
(5, '軒雲', 9, NULL, '74774', NULL, NULL, '2025-11-17 06:45:00', '1763361900460_3.png', 4, NULL, NULL, NULL),
(6, '9595', 5, NULL, '6', NULL, NULL, '2025-11-17 07:29:17', '1763364557566_5.jpg', 2, NULL, NULL, NULL),
(7, '李軒雲', 5626, NULL, '6516516516516516515', NULL, NULL, '2025-11-18 06:40:45', '1763448045149_胺基酸.png', 23, '23', '+62626', '26'),
(8, ' LI YU RONG', 6, NULL, '62262626', NULL, NULL, '2025-11-27 00:41:37', '1764204097559_福西安酸.png', 262, '0', '5', '6'),
(9, '李雨融', 1, NULL, '12121', NULL, NULL, '2025-11-27 00:44:02', '1764204242197_customer-btn.png', 2, '1212', '1221', '12121'),
(10, '2', 56, NULL, '7717', NULL, NULL, '2025-12-12 14:41:18', '1765550478542_福西安酸.png', 14, '171', '171', '171');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `image_filename` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `vendor_name` varchar(255) NOT NULL,
  `pdf_filename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `brand`, `code`, `description`, `image_filename`, `name`, `price`, `spec`, `stock`, `vendor_name`, `pdf_filename`) VALUES
(23, '', '', '本產品依據AGMA標準製造，適用於每日24小時連續運轉，為目前齒輪減速機效率最高之組合。', '310d5861-c8c1-4c1e-b609-7668e9882efc_三亞遊星式減速機.png', '遊星式減速機', NULL, '', NULL, '三亞', '4161763e-a123-47a7-974e-00b5260e820d_佳衛.pdf'),
(25, '', '', '工機-G5齒輪減速馬達，機型 Type : G5BA', 'cb0a46ec-2b92-490f-b931-90f5c80fe753_工機G5齒輪減速馬達.png', '工機G5齒輪減速馬達', NULL, '', NULL, '工機', '137d5ec3-eee6-46e4-90fc-d46bf5ae3e97_工機 G5.pdf'),
(26, '', '', '工機RK斜齒輪/傘齒輪減速機，機型 / TYPE : RKS04', '6db9428b-2242-4c29-b861-3751cf20e3ac_工機RK斜齒輪傘齒輪減速機.png', '工機RK斜齒輪/傘齒輪減速機', NULL, '', NULL, '工機', '76df8e13-12ec-4097-9af7-28cc13d26f6d_工機 RK.pdf'),
(27, '', '', '工機TK/BH行渦輪減速機，為了符合各種產業傳動方面所提供的需求，不斷投入大量的心力資金，以良好的材質加上熱處理極精細研磨加工而成。', '3ca13f3f-efae-48c8-b067-a70317fdb28f_工機渦輪.png', '工機TK/BH行渦輪減速機', NULL, '', NULL, '工機', '46778512-b8d1-47bd-afe1-092173595d3c_工機渦輪.pdf'),
(29, '', '', '台達ASDA-B3 系列伺服系: ASDA-B3 產品特色 新一代控制演算法：可克服機構上剛性不足或撓性結構的問題。 自動調機功能：供無控制理論背景的操作人員，輕鬆完成調機。 便利的增益調整功能：自動偵測慣量的變化，提高控制的精準度。 新一代ECM-B3 馬達：短而小的設計可滿足設備結構小型化與輕量化的需求。', '7fd5e513-823c-47b5-9f3d-cad6464468ab_台達B3標準型交流伺服系統.png', 'ASDA-B3 系列 伺服系統', NULL, '', NULL, '台達', 'd9d664a0-e352-4f5e-a092-56ae96c1098c_DELTA_ASDA-B3_產品型錄.pdf'),
(30, '', '', '台達精巧標準型向量控制變頻器MS300系列，承襲台達變頻器卓越的驅動性能，更具備體積精巧、應用彈性、系統穩定、品質可靠、安裝簡便六大優勢，能在有效的空間利用下提升機台\r\n效率，成功將每一份工業資源的投入轉化為豐碩的成果，創造產業極佳競爭力。', 'ceb3ee28-89f5-4631-95ee-82d34cc670ef_MS300精巧標準型變頻器.png', 'MS300', NULL, '', NULL, '台達', '3326486b-9b3e-4d18-9afc-114dbc93c0c8_MS300產品型錄.pdf'),
(31, '', '', '小型高機能型\r\n200 V級（三相電源用）0.1∼22 kW\r\n200 V級（單相電源用）0.1∼3.7 kW\r\n400 V級（三相電源用）0.2∼30 kW\r\n達到業界頂尖*1的590 Hz！\r\n感應馬達、PM馬達皆達到了最高輸出頻率590 Hz*2的高速運轉。實現了無齒輪、無皮帶，讓機械小型化、效率也提升。全新搭載PM用高級向量控制，可在驅動的同時檢測PM馬達的磁極位置，因此可將電力抑制到最低限度。安川變頻器不只可驅動馬達，更可即時擷取裝置的機械狀態（數據）。透過這些數據，進行「故障預兆診斷」，並與上位「連結」有助於生產管理效率提高。', 'a25d6f07-88fd-4cf9-b02f-ab715e80d695_安川GA500變頻器.png', 'GA500變頻器', NULL, '', NULL, '安川', '46de41d7-10c9-4960-8e9b-dcba12eb5e0c_GA500型錄.pdf'),
(32, '', '', '安川變頻器GA700高性能型\r\n200 V級 0.4∼110 kW\r\n400 V級 0.4∼630 kW\r\nGA700 具有為客戶的設備、機器帶來革命性改變的價值，並實現提高生產力、節能、降低成本，以及提高耐環境性的目標。適用於IPM 馬達時，無編碼器亦可輸出零速200% 轉矩，因此可實現定轉矩機械的小型化以及降低系統成本。適用於感應馬達時，如果使用無PG推進向量控制，即可在無編碼器的情況下實現捲取機的張力控制，可節省配線並提高可靠性。', '6256da86-2b1c-481e-b3b4-41069aef7cbe_安川GA700變頻器.png', 'GA700變頻器', NULL, '', NULL, '安川', '992e0e6e-e90c-40eb-8275-70ba2e4b5d7c_GA700 產品型錄.pdf'),
(33, '', '', '超精密傳動減速機:\r\n精密行星減速機\r\n直角減速機\r\n中空旋轉平台\r\n精密齒輪耐磨耗:本公司行星齒輪與太陽齒輪材料採用SCM4材料，經硬滾、滲碳、熱處理至硬度50~55HRC及齒面研磨加工確保齒輪精度、最佳耐磨耗、耐性韌性，延長使用壽命。\r\n適用於:\r\nCNC加工機、分度分割機械\r\nCNC龍門洗床超精密傳動減速機:\r\n精密行星減速機\r\n直角減速機\r\n中空旋轉平台\r\n精密齒輪耐磨耗:本公司行星齒輪與太陽齒輪材料採用SCM4材料，經硬滾、滲碳、熱處理至硬度50~55HRC及齒面研磨加工確保齒輪精度、最佳耐磨耗、耐性韌性，延長使用壽命。\r\n適用於:\r\nCNC加工機、分度分割機械\r\nCNC龍門銑床、CNC龍門磨床\r\n紡織機械、包裝機械\r\n半導體設備、自動化機械手臂\r\n印刷機械、彎管機械、輸送機\r\n木工機械、線切割機械', 'db2b54ac-e168-4e83-b814-2adc2d64b855_減速機.png', '行星式減速機', NULL, '', NULL, '鼎悠本公司', '537236ec-87d1-43bf-93da-339576071834_鼎悠產品型錄合成版 [20140324].pdf'),
(34, '', '', '鼎悠渦輪渦桿減速機:\r\n提供各式各樣的型號、比數、大小，以應付各式各樣的需求。', '4db03d1f-eff4-4b32-bc53-468859aed883_渦輪減速機.png', '渦輪渦桿減速機', NULL, '', NULL, '鼎悠本公司', '91e838d2-79ff-42cb-ad6a-2d13556a4c70_鼎悠-蝸輪減速機目錄_.pdf'),
(35, '', '', '裡面有各式各樣的聯軸器，聯軸器主要用來做緩衝連軸用的，有上百種的聯軸器，以應變包聯軸器，聯軸器主要用來做緩衝連軸用的，有上百種的聯軸器，以應變包羅萬象的軸與機械', '37dc4ab1-c6aa-40cb-8f75-475c95ba2828_聯軸器.png', '聯軸器', NULL, '', NULL, '鼎悠本公司', 'b5b4d57a-bcbc-4552-91a3-829b2823ee6d_聯軸器-1.pdf'),
(36, '', '', '由於目錄過大，目錄放置於手冊下載專區', '18c7ec55-97d1-4ba9-8b63-e57a7cc89331_vgs馬達.png', 'VGS馬達', NULL, '', NULL, '鑫倍', NULL),
(37, '', '', '伺服單元組合並運轉後，電樞線圈溫度100°C時的值。其他項目為20°C時的值。各值均為代表值。\r\n額定轉矩是指安裝在表中所示尺寸的鋁製散熱片上，且使用環境溫度為40°C時的連續容許轉矩值。\r\n散熱片與減額定率的關係請參閱以下項目。\r\n軸貫穿部分除外。僅使用專用電纜時，符合保護結構規格。\r\n使用附固定制動器的伺服馬達時，請注意以下幾點事項。固定制動器無法用於制動。\r\n固定制動器放開時間及固定制動器動作時間因放電迴路而異。使用時，請務必透過實際產品確認動作延遲時間。\r\n請客戶自備DC24 V電源。\r\n轉子慣性矩的倍率是相對於無固定制動器的標準伺服馬達的值。', 'fb07d3be-69f6-43d7-8700-a4500dbb0634_EX-指令型.png', 'Omega-X 交流伺服馬達', NULL, '', NULL, '安川', 'ce2db579-d2fc-407c-ae94-053f9c461cb7_Σ-X中文型錄.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `logo_filename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`id`, `name`, `logo_filename`) VALUES
(10, '工機', '2bb8500e-5d24-46fb-8eee-a8946102b9a9_工機.png'),
(11, '安川', '8d74745d-89b0-41a3-8f76-867c66137998_安川.png'),
(12, '三亞', 'a696902b-a7f9-4dbb-bd4d-b2783bc0b188_三亞.png'),
(13, '鑫倍', 'cde76cf4-5b64-4d86-83fa-08243d889b9b_鑫倍.png'),
(16, '台達', '2efdbedf-cbec-48d9-ac07-1dd7eab8f605_台達.png'),
(17, '鼎悠本公司', '924ad3fc-117e-45aa-92bf-b72634e24f08_鼎悠.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contact_message`
--
ALTER TABLE `contact_message`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `download_file`
--
ALTER TABLE `download_file`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`news_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `contact_message`
--
ALTER TABLE `contact_message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `download_file`
--
ALTER TABLE `download_file`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `news_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
