INSERT INTO public.roles (id, "name") VALUES(1, 'SUPER_ADMIN') ON CONFLICT (id, name) DO NOTHING;
INSERT INTO public.roles (id, "name") VALUES(2, 'ADMIN') ON CONFLICT (id, name) DO NOTHING;
INSERT INTO public.roles (id, "name") VALUES(3, 'TEACHER') ON CONFLICT (id, name) DO NOTHING;
INSERT INTO public.roles (id, "name") VALUES(4, 'STUDENT') ON CONFLICT (id, name) DO NOTHING;
INSERT INTO public.roles (id, "name") VALUES(5, 'STAFF') ON CONFLICT (id, name) DO NOTHING;
