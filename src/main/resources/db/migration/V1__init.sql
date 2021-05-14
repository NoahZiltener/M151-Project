CREATE
EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE public.auctionhouse_user
(
    id bigint NOT NULL,
    firstname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    lastname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_group character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT auctionhouse_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_16q6cwk1bjyqqbqxhldrduqb1 UNIQUE (username)
);

CREATE SEQUENCE auctionhouse_user_sequence OWNED BY public.auctionhouse_user.id;

CREATE TABLE public.car
(
    id bigint NOT NULL,
    color character varying(255) COLLATE pg_catalog."default" NOT NULL,
    construction_year timestamp without time zone NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT car_pkey PRIMARY KEY (id),
    CONSTRAINT uk_biyf1xj7bo43hek7073jen2l UNIQUE (name)
);

CREATE SEQUENCE car_sequence OWNED BY public.car.id;

CREATE TABLE public.price
(
    id bigint NOT NULL,
    direct_price real NOT NULL,
    starting_bid real NOT NULL,
    CONSTRAINT price_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE price_sequence OWNED BY public.price.id;


CREATE TABLE public.auction
(
    id bigint NOT NULL,
    auction_time timestamp without time zone NOT NULL,
    closed boolean NOT NULL,
    auctioneer_id bigint,
    car_id bigint,
    price_id bigint,
    direct_buy_id bigint,
    CONSTRAINT auction_pkey PRIMARY KEY (id),
    CONSTRAINT uk_s4wqbb0jmi18ypow9cgf15sj7 UNIQUE (closed),
    CONSTRAINT fk14enhkh7dnkh1cdnaptn9llxk FOREIGN KEY (direct_buy_id)
        REFERENCES public.direct_buy (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk4p683jo36sq9k9m0dh8vdg6t5 FOREIGN KEY (car_id)
        REFERENCES public.car (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkdhesn8g2qmrt4k0cwysh06tcl FOREIGN KEY (price_id)
        REFERENCES public.price (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkk7o0jahwu0ddkt0s8j7t9hnty FOREIGN KEY (auctioneer_id)
        REFERENCES public.auctionhouse_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE auction_sequence OWNED BY public.auction.id;

CREATE TABLE public.bid
(
    id bigint NOT NULL,
    bid real NOT NULL,
    auction_id bigint,
    bidder_id bigint,
    CONSTRAINT bid_pkey PRIMARY KEY (id),
    CONSTRAINT fkhexc6i4j8i0tmpt8bdulp6g3g FOREIGN KEY (auction_id)
        REFERENCES public.auction (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fki40oxt91nn37lcvdnvnfgyato FOREIGN KEY (bidder_id)
        REFERENCES public.auctionhouse_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE bid_sequence OWNED BY public.bid.id;

CREATE TABLE public.direct_buy
(
    id bigint NOT NULL,
    buy_price real NOT NULL,
    buyer_id bigint,
    CONSTRAINT direct_buy_pkey PRIMARY KEY (id),
    CONSTRAINT fkcepqy6r88nv1761ia7l8ktfi8 FOREIGN KEY (buyer_id)
        REFERENCES public.auctionhouse_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE direct_buy_sequence OWNED BY public.direct_buy.id;

INSERT INTO public.auctionhouse_user (id, username,firstname, lastname, password, user_group)
VALUES (1, 'noahz', 'Noah', 'Ziltener', crypt('test', gen_salt('bf', 8)), 'ADMIN');