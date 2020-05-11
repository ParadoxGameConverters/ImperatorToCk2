#include "Ck2/Ck2World.h"
#include "Imperator/ImperatorWorld.h"
#include "OutCk2/OutCk2World.h"



int main()
{
	const ImperatorWorld::World impWorld;
	const Ck2World::World ck2World(impWorld);
	outWorld(ck2World);

	return 0;
}