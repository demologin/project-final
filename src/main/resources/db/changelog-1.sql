--liquibase formatted sql
-- todo 2 Удалить социальные сети: vk, yandex.
--changeset sternard:delete_vk
delete from REFERENCE where code = 'vk';

--changeset sternard:delete_tg
delete from REFERENCE where code = 'tg';