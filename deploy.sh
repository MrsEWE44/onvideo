VUEPROJECTDIR="onlineVideo"
JARNAME="onvideo-1.0.jar"
JAVAPROJECT="bin/$JARNAME"

rpmpkg(){
	yum update && yum -y upgrade && yum install -y epel-release &&yum -y upgrade && yum -y install nginx  docker
	makejavaproject
	makevueproject
	makedocker
	makenginx
}


archpkg(){
	pacman -Syu nginx  docker
	makejavaproject
	makevueproject
	makedocker
	makenginx
}

termuxpkg(){
	clear
	apt update && apt upgrade -y && apt install -y openjdk-17 nodejs mariadb nginx
	makenpmsetup
	maketermuxmariadb
	maketermuxvueproject &
	maketermuxjavaproject &
	maketermuxnginx
}

debpkg(){
	apt update && apt upgrade -y && apt install -y nginx  docker
	makejavaproject
	makevueproject
	makedocker
	makenginx
}

makenpmsetup(){
	npm config set registry https://registry.npm.taobao.org
}

maketermuxnginx(){
	clear
	cd bin/
	if [ -f "nginx.conf" ];then
		#nginx -c nginx.conf
		cp nginx.conf ../../../usr/etc/nginx/
		nginx
	else
		exit 1;
	fi
	cd ../
}

makenginx(){
	clear
	cd bin/
	if [ -f "nginx.conf" ];then
		#nginx -c nginx.conf
		cp nginx.conf /etc/nginx/
		nginx
	else
		exit 1;
	fi
	cd ../
}



makedocker(){
	clear
	if [ -f "./bin/Dockerfile" ] && [ -f "Dockerfile" ];then
		systemctl enable docker
		systemctl start docker
		docker build -t onvideojava ./bin/.
		docker build -t onvideomysql ./mysql/.
		docker build -t onvideovue .
		docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123 --restart=always -it onvideomysql
		itname=$(docker ps -a|grep "onvideomysql"|cut -d' ' -f1)
		docker exec -it "$itname" /bin/bash -c "mysql -u root -p123 -e 'create database onvideo'"
		docker exec -it "$itname" /bin/bash -c "mysql -u root -p123 -D onvideo < onvideo.sql"
		docker run -d -p 8885:8885 --restart=always -it onvideojava
		docker run -d --net="host" -p 8898:8898 --restart=always -it onvideovue
	else
		echo "not found Dockerfile !!!"
		exit 0;
	fi
}

maketermuxmariadb(){
	clear
	cd bin/
	if [ -f "onvideo.sql" ];then
		mysqld &
		sleep 4
		mysql -u $(whoami) -e "use mysql;set password for 'root'@'localhost'=password('123');flush privileges;"
		mysql -u root -p123 -e "create database onvideo"
		mysql -u root -p123 -D onvideo < onvideo.sql
	else
		echo "not found onvideo.sql file !!!"
		exit 1;
	fi
	cd ../
}


makevueproject(){
	if [ -d "$VUEPROJECTDIR" ];then
		cd "$VUEPROJECTDIR"
		 sed -i 's/"chromedriver": "^2.27.2",/ /g' package.json
		
		cd ../
	else
		echo "not found $VUEPROJECTDIR work folder !"
		exit 1;
	fi
}

maketermuxvueproject(){
	if [ -d "$VUEPROJECTDIR" ];then
		cd "$VUEPROJECTDIR"
		 sed -i 's/"chromedriver": "^2.27.2",/ /g' package.json
		npm install
		npm run dev
		cd ../
	else
		echo "not found $VUEPROJECTDIR work folder !"
		exit 1;
	fi
}

makejavaproject(){
	if [ -f "$JAVAPROJECT" ];then
		cd bin/
		sed -i 's/192.168.136.129:3307/127.0.0.1:3306/g' conf/application.yml
		sed -i 's/ovideo/onvideo/g' conf/application.yml
		cd ../
	else
		echo "not found $JAVAPROJECT jar file !"
		exit 1;
	fi

}

maketermuxjavaproject(){
	if [ -f "$JAVAPROJECT" ];then
		cd bin/
		sed -i 's/192.168.136.129:3307/127.0.0.1:3306/g' conf/application.yml
		sed -i 's/ovideo/onvideo/g' conf/application.yml
		sh runjava.sh
		cd ../
	else
		echo "not found $JAVAPROJECT jar file !"
		exit 1;
	fi

}

main(){
	clear
	echo -n "1.debian\n2.redhat\n3.arch\n4.termux\nselect : "
	read xx
	case $xx in
	1)
	debpkg
	;;
	2)
	rpmpkg
	;;
	3)
	archpkg
	;;
	4)
	termuxpkg
	;;
	*)
	main
	;;
	esac
		
}

main


