create table if not exists categoria(
    id serial,
    nome varchar(50) not null,
    codigo varchar(20) unique not null,
    ativo boolean default true,
    primary key(id)
);

create table if not exists usuario(
    id serial,
    nome varchar(50) not null,
    email varchar(50) unique not null,
    senha varchar(255) not null,
    admin boolean default false,
    ativo boolean default true,
    primary key(id)
);

create table if not exists partida(
    id serial,
    hora_inicio timestamp not null,
    categoria_id bigint,
    usuario_id bigint not null,
    primary key(id),
    foreign key(categoria_id) references categoria(id),
    foreign key(usuario_id) references usuario(id)
);

create table if not exists questao(
    id serial,
    descricao varchar(255) not null,
    ativo boolean default true,
    categoria_id bigint not null,
    foreign key(categoria_id) references categoria(id),
    primary key(id)
);

create table if not exists resposta(
    id serial,
    descricao varchar(255) not null,
    certa boolean not null,
    ativo boolean default true,
    questao_id bigint not null,
    foreign key(questao_id) references questao(id),
    primary key(id)
);

create table if not exists partida_respostas(
    id serial,
    partida_id bigint not null,
    questao_id bigint not null,
    resposta_id bigint not null,
    primary key(id),
    foreign key(partida_id) references partida(id),
    foreign key(questao_id) references questao(id),
    foreign key(resposta_id) references resposta(id)
);

insert into usuario values
        (1,'ADM','adm@gmail.com','$2a$11$XilNBGp.QrHQRDgkwj3.1u029gG04CyPT2Zj1aX/EK/F3ZwZrNInS','true','true'),
        (2,'ADM 2','adm2@gmail.com','$2a$11$XilNBGp.QrHQRDgkwj3.1u029gG04CyPT2Zj1aX/EK/F3ZwZrNInS','true','true'),
        (3,'joão','joao@gmail.com','$2a$11$XilNBGp.QrHQRDgkwj3.1u029gG04CyPT2Zj1aX/EK/F3ZwZrNInS','false','true');