-- public.prato definition

-- Drop table

-- DROP TABLE public.prato;

CREATE TABLE public.prato (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	descricao varchar(255) NULL,
	nome varchar(255) NULL,
	preco float8 NULL,
	restaurante_id int8 NULL,
	CONSTRAINT prato_pkey PRIMARY KEY (id)
);


-- public.prato foreign keys

ALTER TABLE public.prato ADD CONSTRAINT fkjmil84a0nrk37xaq8yh7coa78 FOREIGN KEY (restaurante_id) REFERENCES restaurante(id);
