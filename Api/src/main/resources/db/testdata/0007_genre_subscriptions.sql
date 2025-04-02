-- Sample genre subscriptions
-- Assume genre_id 1 = Action, genre_id 2 = Fantasy, genre_id 3 = Sci-Fi
-- Assume user_id 1 = admin, 2 = user, 3 = anna

INSERT INTO genre_subscriptions (user_id, genre_id) VALUES
                                                        (1, 8),  -- admin subscribes to Action
                                                        (2, 1),  -- user subscribes to Action
                                                        (3, 2),  -- anna subscribes to Fantasy
                                                        (2, 3);  -- user subscribes to Sci-Fi
