ALTER TABLE public.auction add COLUMN winner_id bigint ;

ALTER TABLE public.auction add CONSTRAINT winnerid FOREIGN KEY (winner_id)
    REFERENCES public.auctionhouse_user (id) MATCH SIMPLE;
