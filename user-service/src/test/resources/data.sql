INSERT INTO public.users (birth_date, latitude, longitude, create_date, update_date, phone_number, status, username,
                          email, name, surname)
VALUES ('1990-01-01', 40.7128, -74.006, '2024-03-12 22:31:18.468499', '2024-03-12 22:31:18.468499', '+1235557890',
        'ACTIVE', 'john_doe', 'john.doe@example.com', 'John', 'Doe');
INSERT INTO public.user_reviews (create_date, update_date, user_id, comment, restaurant_id, score)
VALUES ('2024-03-12 22:31:21.370985', '2024-03-12 22:31:21.370985', 100, 'Great restaurant!', 'restaurant-1', 'FOUR');
