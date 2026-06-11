CREATE TABLE users (
                       id uuid NOT NULL,
                       username varchar(255) NOT NULL,
                       email varchar(255) NOT NULL,
                       "password" varchar(255) NOT NULL,
                       avatar_url varchar(255) NULL,
                       bio text NULL,
                       total_score int4 NOT NULL,
                       role varchar(50) NOT NULL,
                       created_at timestamp(6) NULL,
                       updated_at timestamp(6) NULL,
                       CONSTRAINT uk_user_email UNIQUE (email),
                       CONSTRAINT uk_user_username UNIQUE (username),
                       CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE diy_projects (
                              id uuid NOT NULL,
                              user_id uuid NOT NULL,
                              title varchar(255) NOT NULL,
                              "content" text NOT NULL,
                              previous_content text NULL,
                              average_rating float8 NULL,
                              total_votes int4 NULL,
                              is_public bool NULL,
                              is_finished bool NULL,
                              created_at timestamp(6) NULL,
                              updated_at timestamp(6) NULL,
                              CONSTRAINT diy_projects_pkey PRIMARY KEY (id),
                              CONSTRAINT fk_diy_projects_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_diy_user ON diy_projects USING btree (user_id);

CREATE TABLE interactions (
                              id uuid NOT NULL,
                              user_id uuid NOT NULL,
                              entity_id uuid NOT NULL,
                              entity_type varchar(255) NOT NULL,
                              "type" varchar(255) NOT NULL,
                              "content" text NULL,
                              previous_content text NULL,
                              created_at timestamp(6) NULL,
                              updated_at timestamp(6) NULL,
                              CONSTRAINT interactions_pkey PRIMARY KEY (id),
                              CONSTRAINT fk_interactions_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_interaction_entity ON interactions USING btree (entity_id, entity_type);

CREATE TABLE media (
                       id uuid NOT NULL,
                       user_id uuid NOT NULL,
                       entity_id uuid NOT NULL,
                       entity_type varchar(255) NOT NULL,
                       media_type varchar(255) NOT NULL,
                       url varchar(255) NOT NULL,
                       created_at timestamp(6) NULL,
                       CONSTRAINT media_pkey PRIMARY KEY (id),
                       CONSTRAINT fk_media_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_media_entity ON media USING btree (entity_id, entity_type);
CREATE INDEX idx_media_user ON media USING btree (user_id);

CREATE TABLE observation_plans (
                                   id uuid NOT NULL,
                                   user_id uuid NOT NULL,
                                   target_name varchar(255) NOT NULL,
                                   planned_timestamp timestamp(6) NOT NULL,
                                   notify_in_advance bool NULL,
                                   status varchar(255) NOT NULL,
                                   created_at timestamp(6) NULL,
                                   updated_at timestamp(6) NULL,
                                   CONSTRAINT observation_plans_pkey PRIMARY KEY (id),
                                   CONSTRAINT fk_observation_plans_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_plan_date ON observation_plans USING btree (planned_timestamp);
CREATE INDEX idx_plan_user ON observation_plans USING btree (user_id);

CREATE TABLE observation_posts (
                                   id uuid NOT NULL,
                                   user_id uuid NOT NULL,
                                   plan_id uuid NULL,
                                   target_name varchar(255) NOT NULL,
                                   equipment_metadata jsonb NULL,
                                   previous_equipment_metadata jsonb NULL,
                                   average_rating float8 NULL,
                                   total_votes int4 NULL,
                                   created_at timestamp(6) NULL,
                                   updated_at timestamp(6) NULL,
                                   CONSTRAINT observation_posts_pkey PRIMARY KEY (id),
                                   CONSTRAINT fk_observation_posts_user FOREIGN KEY (user_id) REFERENCES users(id),
                                   CONSTRAINT fk_observation_posts_plan FOREIGN KEY (plan_id) REFERENCES observation_plans(id)
);
CREATE INDEX idx_post_plan ON observation_posts USING btree (plan_id);
CREATE INDEX idx_post_user ON observation_posts USING btree (user_id);