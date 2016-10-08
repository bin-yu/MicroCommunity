create table xiao_qu(id bigint NOT NULL auto_increment primary key, name varchar(128));
create table ye_zhu(xiao_qu_id bigint NOT NULL, id bigint NOT NULL AUTO_INCREMENT, name varchar(128) NOT NULL, mobile_number varchar(24));
alter table ye_zhu add constraint ye_zhu_pk PRIMARY KEY(id);
alter table ye_zhu add constraint ye_zhu_fk_xiao_qu FOREIGN KEY(xiao_qu_id) REFERENCES xiao_qu(id);
create table jiao_fei_dang(ye_zhu_id bigint NOT NULL,month int NOT NULL,type varchar(8) NOT NULL,amount double NOT NULL,status bool DEFAULT FALSE);
alter table jiao_fei_dang add constraint jiao_fei_dang_pk PRIMARY KEY(ye_zhu_id,month);
alter table jiao_fei_dang add constraint jiao_fei_dang_fk_ye_zhu FOREIGN KEY(ye_zhu_id) REFERENCES ye_zhu(id);

create table gong_zhong_hao (xiao_qu_id bigint NOT NULL, app_id varchar(32) primary key,app_secret varchar(32),name varchar(48));
alter table gong_zhong_hao add constraint gong_zhong_hao_fk_xiao_qu FOREIGN KEY(xiao_qu_id) REFERENCES xiao_qu(id);
create table wei_xin_ye_zhu_bang_ding(app_id varchar(32) NOT NULL,openId varchar(32) NOT NULL,uuid varchar(32),ye_zhu_id bigint NOT NULL);
alter table wei_xin_ye_zhu_bang_ding add constraint wei_xin_ye_zhu_bang_ding_pk PRIMARY KEY(openId,ye_zhu_id);
alter table wei_xin_ye_zhu_bang_ding add constraint wei_xin_ye_zhu_bang_ding_fk_ye_zhu FOREIGN KEY(ye_zhu_id) REFERENCES ye_zhu(id);
alter table wei_xin_ye_zhu_bang_ding add constraint wei_xin_ye_zhu_bang_ding_fk_gong_zhong_hao FOREIGN KEY(app_id) REFERENCES gong_zhong_hao(app_id);