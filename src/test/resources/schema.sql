drop table public.days_of_week if exists;
drop table public.genre if exists;
drop table public.schedule if exists;
drop table public.schedule_days_of_week if exists;
drop table public.show if exists;
drop table public.episode if exists;
drop table public.season if exists;
drop table public.show_genre if exists;
drop table public.users if exists;
drop table public.users_roles if exists;

create table public.days_of_week
(
  day_of_week_id integer primary key auto_increment,
  day            varchar(255) not null unique
);
create table public.genre
(
  genre_id integer primary key auto_increment,
  name     varchar(255) not null unique
);
create table public.schedule
(
  schedule_id  integer primary key auto_increment,
  time_of_show time
);
create table public.schedule_days_of_week
(
  schedule_id     integer not null,
  days_of_week_id integer not null,
  primary key (schedule_id, days_of_week_id),
  constraint fk_schedule_days_of_week_days_of_week foreign key (days_of_week_id) references public.days_of_week,
  constraint fk_schedule_days_of_week_schedule foreign key (schedule_id) references public.schedule
);


create table public.show
(
  show_id          integer PRIMARY KEY,
  name             varchar(255)  not null unique,
  type             varchar(255)  not null,
  status           varchar(255)  not null,
  language         varchar(255)  not null,
  premiered        date          not null,
  runtime          integer       not null,
  schedule_id      integer       not null,
  image_url        varchar(255)  not null,
  imdb_url         varchar(255)  not null,
  offical_site_url varchar(255)  not null,
  tv_maze_url      varchar(255)  not null,
  summary          varchar(5000) not null,
  last_update      timestamp     not null,
  constraint fk_show_schedule_id foreign key (schedule_id) references public.schedule
);

create index idxshow_name on public.show (name);

create table public.show_genre
(
  show_id  integer not null,
  genre_id integer not null,
  constraint fk_show_genre_show foreign key (genre_id) references public.genre,
  constraint fk_show_genre_genre foreign key (show_id) references public.show
);

create table public.season
(
  season_id     integer PRIMARY KEY,
  end_date      date          not null,
  episode_order integer       not null,
  image_url     varchar(255)  not null,
  number        integer       not null,
  premiere_date date          not null,
  summary       varchar(5000) not null,
  tvmaze_url    varchar(255)  not null,
  show_id       integer,
  constraint fk_season_show foreign key (show_id) references public.show
);

create index idxseason on public.season (number);


create table public.episode
(
  episode_id integer PRIMARY KEY,
  airtdate   date,
  airtime    time,
  image      varchar(255),
  name       varchar(255),
  number     integer,
  runtime    integer,
  summary    varchar(5000),
  tvmaze_url varchar(255),
  season_id  integer,
  constraint fk_episode_season foreign key (season_id) references public.season
);

create index idxepisode_name on public.episode (name);
create index idxepisode_number on public.episode (number);


create table public.users
(
  user_id           integer PRIMARY KEY AUTO_INCREMENT,
  username          VARCHAR(255),
  password          VARCHAR(255),
  registration_date TIMESTAMP,
  active            BOOLEAN,
  email             VARCHAR(255)
);

create table public.users_roles
(
  id integer primary key AUTO_INCREMENT,
  user_id integer      NOT NULL,
  role    varchar(255) NOT NULL,
  constraint fk_users_users_roles foreign key (user_id) references public.users
);





