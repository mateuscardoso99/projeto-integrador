create table if not exists categoria(
    id bigint serial,
    nome varchar(50) not null,
    primary key(id)
);

create table if not exists usuario(
    id bigint serial,
    email varchar(50) unique not null,
    senha varchar(255) not null,
    primary key(id)
);

create table if not exists partida(
    id bigint serial,
    horaInicio datetime not null,
    categoria_id bigint,
    usuario_id bigint not null,
    primary key(id),
    foreign key(categoria_id) references categoria(id),
    foreign key(usuario_id) references usuario(id),
);

create table if not exists questao(
    id bigint serial,
    descricao varchar(255) not null,
    ativo boolean not null,
    categoria_id bigint not null,
    foreign key(categoria_id) references categoria(id),
    primary key(id)
);

create table if not exists resposta(
    id bigint serial,
    descricao varchar(255) not null,
    certa boolean not null,
    questao_id bigint not null,
    foreign key(questao_id) references questao(id),
    primary key(id)
);

create table if not exists partida_respostas(
    id bigint serial,
    partida_id bigint not null,
    questao_id bigint not null,
    resposta_id bigint not null,
    primary key(id),
    foreign key(partida_id) references partida(id),
    foreign key(questao_id) references questao(id),
    foreign key(resposta_id) references resposta(id),
);