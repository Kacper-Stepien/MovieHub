-- Subscriptions (user_id <-> movie_id)
-- Assume movie_id 1 = Władca Pierścieni, movie_id 2 = Star Wars
-- Assume user_id 1 = admin@example.com, 2 = user@example.com, 3 = anna.kowalska@example.com

INSERT INTO movie_subscriptions (user_id, movie_id) VALUES
                                                        (1, 1),
                                                        (2, 1),
                                                        (3, 2),
                                                        (2, 2);
