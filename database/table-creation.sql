-- Table: public.elk

-- DROP TABLE IF EXISTS public.elk;

CREATE TABLE IF NOT EXISTS public.elk
(
    id uuid NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.elk
    OWNER to postgres;

-- Table: public.bugle

-- DROP TABLE IF EXISTS public.bugle;

CREATE TABLE IF NOT EXISTS public.bugle
(
    bugle_id uuid NOT NULL,
    user_id uuid NOT NULL,
    bugle character varying COLLATE pg_catalog."default" NOT NULL,
    create_date timestamp with time zone NOT NULL,
    CONSTRAINT bugle_pkey PRIMARY KEY (bugle_id),
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES public.elk (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.bugle
    OWNER to postgres;

-- Table: public.bonds

-- DROP TABLE IF EXISTS public.bonds;

CREATE TABLE IF NOT EXISTS public.bonds
(
    user_id uuid NOT NULL,
    following uuid NOT NULL,
    CONSTRAINT bond PRIMARY KEY (user_id, following),
    CONSTRAINT following FOREIGN KEY (following)
        REFERENCES public.elk (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES public.elk (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.bonds
    OWNER to postgres;