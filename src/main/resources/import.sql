DROP TABLE IF EXISTS "public"."roles";

-- Table Definition
CREATE TABLE "public"."roles"
(
    "id"   int8         NOT NULL,
    "name" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."users";

-- Table Definition
CREATE TABLE "public"."users"
(
    "id"              int8        NOT NULL,
    "username"        varchar(30) NOT NULL,
    "password"        varchar(72) NOT NULL,
    "email"           varchar(50) NOT NULL,
    "account_enabled" bool        NOT NULL,
    "account_locked"  bool        NOT NULL,
    "email_verified"  bool        NOT NULL,
    "created_at"      timestamp   NOT NULL,
    "updated_at"      timestamp   NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."users_data";

-- Table Definition
CREATE TABLE "public"."users_data"
(
    "id"       int8         NOT NULL,
    "user_id"  int8,
    "name"     varchar(255) NOT NULL,
    "lastname" varchar(255) NOT NULL,
    "gender"   int2 CHECK ((gender >= 0) AND (gender <= 2)),
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."users_roles";

-- Table Definition
CREATE TABLE "public"."users_roles"
(
    "user_id" int8 NOT NULL,
    "role_id" int8 NOT NULL
);

INSERT INTO "public"."roles" ("id", "name")
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO "public"."users" ("id", "account_enabled", "account_locked", "created_at", "email", "email_verified",
                              "password", "updated_at", "username")
VALUES (1, 't', 'f', '2024-04-22 20:30:57.150702', 'admin@rgdevment.cl', 'f',
        '$2a$10$HeqcrD2VZljvMdPn5klLje4Q3kk5r4T6hDfO/mkfbICSZ2OJAVydm', '2024-04-22 20:30:57.150728', 'admin');

INSERT INTO "public"."users_roles" ("user_id", "role_id")
VALUES (1, 1),
       (1, 2);

-- Indices
CREATE UNIQUE INDEX uk_ofx66keruapi6vyqpv6f2or37 ON public.roles USING btree (name);

-- Indices
CREATE INDEX idx_username ON public.users USING btree (username);
CREATE INDEX idx_email ON public.users USING btree (email);
CREATE UNIQUE INDEX uk_6dotkott2kjsp8vw4d0m25fb7 ON public.users USING btree (email);
CREATE UNIQUE INDEX uk_r43af9ap4edm43mmtq01oddj6 ON public.users USING btree (username);
ALTER TABLE "public"."users_data"
    ADD FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id");

-- Indices
CREATE UNIQUE INDEX uk_mgd853e46owfhatjusl2jsdq0 ON public.users_data USING btree (user_id);
ALTER TABLE "public"."users_roles"
    ADD FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id");
ALTER TABLE "public"."users_roles"
    ADD FOREIGN KEY ("role_id") REFERENCES "public"."roles" ("id");

-- Indices
CREATE UNIQUE INDEX ukq3r1u8cne2rw2hkr899xuh7vj ON public.users_roles USING btree (user_id, role_id);
