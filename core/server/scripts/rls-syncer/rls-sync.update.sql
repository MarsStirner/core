USE `rls1`;
INSERT INTO `rlsTradeName` (`id`, `name`) VALUES (1, "Метронидазол");
INSERT INTO `rlsInpName` (`id`, `latName`, `name`) VALUES (1, "Metronidazole*", "Метронидазол*");
INSERT INTO `rlsDosage` (`id`, `name`) VALUES (1, "5 мг/мл");
INSERT INTO `rlsForm` (`id`, `name`) VALUES (1, "р-р д/инф.");
INSERT INTO `rlsFilling` (`id`, `name`) VALUES (1, "фл. ПЭ 100 мл");
INSERT INTO `rlsPacking` (`id`, `name`) VALUES (1, "пак. п/пропилен. 1");
INSERT INTO `rlsNomen` (`id`, `code`, `packing_id`, `filling_id`, `form_id`, `INPName_id`, `tradeName_id`, `dosage_id`) VALUES (1, 228981, 1, 1, 1, 1, 1, 1);
