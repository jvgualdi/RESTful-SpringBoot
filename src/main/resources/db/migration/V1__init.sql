CREATE TABLE `company` (
    `id` bigint(20) NOT NULL,
    `cnpj` varchar(255) NOT NULL,
    `data_atualizacao` datetime NOT NULL,
    `data_criacao` datetime NOT NULL,
    `razao_social` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee`(
    `id` bigint(20) NOT NULL,
    `cpf` varchar(255) NOT NULL,
    `data_atualizacao` datetime NOT NULL,
    `data_criacao` datetime NOT NULL,
    `email` varchar(255) NOT NULL,
    `nome` varchar(255) NOT NULL,
    `perfil` varchar(255) NOT NULL,
    `horas_almoco` float DEFAULT NULL,
    `horas_trabalhadas_dia` float DEFAULT NULL,
    `senha` varchar(255) NOT NULL,
    `valor_hora` decimal(19,2) DEFAULT NULL,
    `company_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `release` (
   `id` bigint(20) NOT NULL,
   `data` datetime NOT NULL,
   `data_atualizacao` datetime NOT NULL,
   `data_criacao` datetime NOT NULL,
   `descricao` varchar(255) NOT NULL,
   `localizacao` varchar(255) NOT NULL,
   `tipo` varchar(255) NOT NULL,
   `employee_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- config company
--
ALTER TABLE `company`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `company`
    MODIFY `id` bigint (20) NOT NULL AUTO_INCREMENT;

INSERT INTO `company` ( `id` , `cnpj` , `data_atualizacao` , `data_criacao` , `razao_social` )
VALUES ( NULL , '82198127000121' , CURRENT_DATE (), CURRENT_DATE (), 'EmpresaADMIN' );

--
-- config employee
--
ALTER TABLE `employee`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FK4cm1kg523jlopyexjbmi6y54j` (`company_id`);

ALTER TABLE `employee`
    MODIFY `id` bigint (20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `employee`
    ADD CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- config release
--
ALTER TABLE `release`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FK46i4k5vl8wah7feutyes9kbpi4` (`employee_id`);

ALTER TABLE `release`
    MODIFY `id` bigint (20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `release`
    ADD CONSTRAINT `FK46i4k5vl8wah7feutyes9kbpi4` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);
