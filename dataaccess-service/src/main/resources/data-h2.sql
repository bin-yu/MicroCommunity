insert into xiao_qu(name) values('Bin-Test');
insert into ye_zhu(xiao_qu_id,name,mobile_number) values ((select id from xiao_qu where name='Bin-Test'),'Bin','18888888888');
insert into jiao_fei_dang values((select id from ye_zhu where name='Bin'),201601,'WUYE',100.0,FALSE);
insert into gong_zhong_hao values((select id from xiao_qu where name='Bin-Test'),'wx73f1e72a530acc34','d8d6ca01bde0c496e019d9fa837db4e0','Bin-Test');
insert into wei_xin_ye_zhu_bang_ding values('wx73f1e72a530acc34','ogxnmv4fzsAqBe-usqpoBc3VMIIs','o6_bmasdasdsad6_2sgVt7hMZOPfL',(select id from ye_zhu where name='Bin'));