insert into blabber_user (id, username, password_hash) values (1, 'admin', '$2a$10$ocRq7CNvu3HEYxkM8YXpledGrQXea9FhxrLhGCL3iQZ1BZUgmpbSe');

insert into blog (id, code, name) values (1, 'sbb', 'Spring Boot Basics');

insert into blabber_user (id, first_name, last_name, username, password_hash, blog_id) values (2, 'Admir', 'Memic', 'admir', '$2a$10$jpzZpnXmbfCNZ9FiHd7GF.xcB/Ke3csVnA5yDz9JyMqpGwf7aLytu', 1);
insert into blabber_user (id, first_name, last_name, username, password_hash) values (3, 'Emir', 'Memic', 'emir', '$2a$06$djNzcrPOyLtA.y2FPzaByOIM6hnZWi6P5iNy4Cj/KHQWPg7PYMwhu');

insert into category (id, code, name) values (1, 'programming', 'Programming');
insert into post (id, title, content) values (1, 'Spring Boot Rest Example', 'This post describes the basic step to create a RESTful web application using Spring Boot');
insert into category_posts (category_id, posts_id) values (1, 1);

insert into blog_categories (blog_id, categories_id) values (1, 1);
insert into comment (id, content, blabber_user_id) values (1, 'Not bad!', 2);
insert into post_comments (post_id, comments_id) values (1, 1);