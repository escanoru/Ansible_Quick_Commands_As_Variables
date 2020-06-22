def setDescription() { 
  def item = Jenkins.instance.getItemByFullName(env.JOB_NAME) 
  item.setDescription("<h3><span style=\"color:green\">Ansible Job to send multiple commands to multiple targets nodes</span></h3>") 
  item.save()
  }
setDescription()

pipeline {
	// agent any
	agent { label 'ansible' }
	options {
		ansiColor('gnome-terminal')
		buildDiscarder(logRotator(daysToKeepStr: '180'))
		}
  
  parameters {
        string(
		name: 'Target_Host', 
		description: '<h4>Node ip separated by comma where Telegraf will be installed, e.g 15.214.x.x, 15.214.x.x, 15.214.x.x, 15.214.x.x.</h4>'
		)
        password(
		name: 'Host_Password', 
		defaultValue: '', 
		description: '<h4>Host root\'s password.</h4>'
		)
        string(
		name: 'Command_01', 
		defaultValue: '', 
		description: '<h4>Command to run</h4>'
		)
        string(
		name: 'Command_02', 
		defaultValue: '', 
		description: '<h4>Command to run<</h4>'
		)
        string(
		name: 'Command_03', 
		defaultValue: '', 
		description: '<h4>Command to run</h4>'
		)
        string(
		name: 'Command_04', 
		defaultValue: '', 
		description: '<h4>Command to run</h4>'
		)
        string(
		name: 'Command_05', 
		defaultValue: '', 
		description: '<h4>Command to run</h4>'
		)		
    }
	
            	
  stages { 

	stage('Check Ansible Inventory File') {
      steps {
        sh '''
		   echo -e "[nodes]\\n" >  ${WORKSPACE}/inventory.ini | cat ${WORKSPACE}/inventory.ini
           echo ${Target_Host} | sed \'s/,/\\n/g\' | while read line ; do sed -i \'/\\[nodes\\]/a \\\'"${line}"\'\' ${WORKSPACE}/inventory.ini ; done
		   '''
      }
    }
	
    stage('Ansible Role Task') {
      steps {
        ansiblePlaybook(
        playbook: '${WORKSPACE}/quick_command_pass_as_variable.yaml',
        inventory: '${WORKSPACE}/inventory.ini',
        colorized: true,
		extras: '--ssh-extra-args="-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"',
        extraVars: [
		    command_01: '${Command_01}',
            command_02: '${Command_02}',
            command_03: '${Command_03}',
			command_04: '${Command_04}',
			command_05: '${Command_05}',
            ansible_password: [value: '${Host_Password}', hidden: true]
        ])
	  }	
    }		
		
    stage('Clean Inventory File') {
      steps {
        sh '''
		   echo -e "[nodes]\\n]" >  ${WORKSPACE}/inventory.ini | cat ${WORKSPACE}/inventory.ini
		   '''
     }
    }
  }
}
